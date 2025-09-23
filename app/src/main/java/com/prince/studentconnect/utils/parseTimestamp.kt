package com.prince.studentconnect.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
fun parseTimestamp(sentAt: String): Long {
    return try {
        // Case 1: It's a number (epoch millis)
        sentAt.toLongOrNull()?.let { return it }

        // Case 2: It's an ISO-8601 string
        Instant.parse(sentAt).toEpochMilli()
    } catch (e: Exception) {
        // Fallback: return "now"
        Instant.now().toEpochMilli()
    }
}
