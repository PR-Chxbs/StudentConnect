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

/**
 * Worker that syncs PendingEvent rows to remote EventApi.
 * It uses ServiceLocator to obtain EventApi / repositories if available in project.
 */
@RequiresApi(Build.VERSION_CODES.O)
class SyncPendingEventsWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG = "SyncPendingEventsWorker"
    private val db = AppDatabase.getInstance(context)
    // The project already has a ServiceLocator providing retrofit/repositories.
    // We will try to access EventApi via ServiceLocator; fallback to direct retrofit if needed.
    private val eventApi: EventApi by lazy { ServiceLocator.eventApi } // you may need to expose this in ServiceLocator

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val pending = db.pendingEventDao().getAll()
            if (pending.isEmpty()) {
                Log.d(TAG, "No pending events to sync")
                return@withContext Result.success()
            }

            var allOk = true
            for (p in pending) {
                val req = toCreateRequest(p)
                try {
                    val response = eventApi.createEvent(req)
                    if (response.isSuccessful) {
                        // remove the pending record
                        db.pendingEventDao().deleteById(p.id)
                    } else {
                        Log.w(TAG, "Server rejected pending event id=${p.id} code=${response.code()}")
                        allOk = false
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Network error while syncing id=${p.id}: ${e.message}", e)
                    allOk = false
                }
            }

            return@withContext if (allOk) Result.success() else Result.retry()
        } catch (e: Exception) {
            Log.e(TAG, "Worker failure: ${e.message}", e)
            return@withContext Result.retry()
        }
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


