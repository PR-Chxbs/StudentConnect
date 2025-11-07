package com.prince.studentconnect.work

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prince.studentconnect.data.local.db.AppDatabase
import com.prince.studentconnect.data.local.db.PendingEvent
import com.prince.studentconnect.data.remote.api.EventApi
import com.prince.studentconnect.data.remote.dto.event.CreateEventRequest
import com.prince.studentconnect.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * Worker that syncs PendingEvent rows to remote EventApi / EventRepository.
 *
 * Behavior:
 * - Reads pending events from Room.
 * - Attempts to create each event on the server using ServiceLocator.eventRepository (preferred).
 * - Falls back to ServiceLocator.eventApi if repository call isn't available.
 * - Deletes pending row on success. Keeps row & returns Result.retry() on transient failures.
 */
@RequiresApi(Build.VERSION_CODES.O)
class SyncPendingEventsWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG = "SyncPendingEventsWorker"
    private val db = AppDatabase.getInstance(context)

    // Prefer repository (reuses auth/interceptors/business logic)
    private val eventRepository = try {
        ServiceLocator::class.java.getDeclaredField("eventRepository").let {
            it.isAccessible = true
            it.get(ServiceLocator)
        }
    } catch (_: Throwable) {
        null
    }

    // Fallback to raw API
    private val eventApi: EventApi by lazy { ServiceLocator.eventApi }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val pending = db.pendingEventDao().getAll()
            if (pending.isEmpty()) {
                Log.d(TAG, "No pending events to sync")
                return@withContext Result.success()
            }

            var anyFailure = false

            for (p in pending) {
                val req = toCreateRequest(p)
                try {
                    val success = trySendRequest(req)
                    if (success) {
                        // remove the pending record
                        db.pendingEventDao().deleteById(p.id)
                        Log.d(TAG, "Synced pending event id=${p.id}")
                    } else {
                        Log.w(TAG, "Server rejected pending event id=${p.id}")
                        anyFailure = true
                    }
                } catch (e: IOException) {
                    // network I/O problem -> retry later
                    Log.e(TAG, "Network I/O error while syncing id=${p.id}: ${e.message}", e)
                    anyFailure = true
                } catch (e: Exception) {
                    Log.e(TAG, "Unexpected error while syncing id=${p.id}: ${e.message}", e)
                    anyFailure = true
                }
            }

            return@withContext if (anyFailure) Result.retry() else Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Worker failure: ${e.message}", e)
            return@withContext Result.retry()
        }
    }

    /**
     * Try to send the request via repository if present, otherwise use eventApi.
     * Returns true if server responded with success and the event is considered created.
     */
    private suspend fun trySendRequest(req: CreateEventRequest): Boolean {
        // If repository is available, call its create method. The exact signature may differ;
        // we try a few common possibilities to be robust. Adapt if your EventRepository has a different name/signature.
        eventRepository?.let { repoObj ->
            try {
                // Reflection attempt: common method name 'createEvent' accepting CreateEventRequest.
                val method = repoObj::class.java.methods.firstOrNull { m ->
                    m.name == "createEvent" && m.parameterTypes.size == 1
                }
                if (method != null) {
                    val result = method.invoke(repoObj, req)
                    // If the repository returns retrofit2.Response
                    if (result is Response<*>) {
                        return result.isSuccessful
                    }
                    // If the repository returns a wrapper (e.g. Result) or boolean, attempt to interpret:
                    if (result is Boolean) return result
                    // If it's some custom wrapper, try to inspect `isSuccessful` or `success` field
                    val isSuccessfulField =
                        result?.javaClass?.kotlin?.members?.firstOrNull { it.name.equals("isSuccessful", true) || it.name.equals("success", true) }
                    if (isSuccessfulField != null) {
                        val v = isSuccessfulField.call(result)
                        if (v is Boolean) return v
                    }
                    // If none of the above, fall through to API call
                }
            } catch (t: Throwable) {
                Log.w(TAG, "Repository createEvent invocation failed: ${t.message}. Falling back to direct API.", t)
            }
        }

        // Fallback: direct EventApi call. Assuming eventApi.createEvent is a suspend function returning retrofit2.Response
        val response: Response<*> = eventApi.createEvent(req)
        // Log response details for debugging (avoid logging secrets)
        val code = response.code()
        val bodyStr = try {
            response.errorBody()?.string() ?: response.body()?.toString()
        } catch (_: Exception) {
            null
        }
        Log.d(TAG, "EventApi response code=$code body=${bodyStr?.take(200)}")
        return response.isSuccessful
    }

    private fun toCreateRequest(p: PendingEvent): CreateEventRequest {
        // Map PendingEvent -> CreateEventRequest (matching your DTO package)
        return CreateEventRequest(
            creator_id = p.creatorId,
            conversation_id = p.conversationId,
            title = p.title,
            description = p.description,
            start_at = p.startAt,
            icon_url = p.iconUrl,
            color_code = p.colorCode,
            recurrence_rule = p.recurrenceRule,
            reminder_at = p.reminderAt
        )
    }
}
