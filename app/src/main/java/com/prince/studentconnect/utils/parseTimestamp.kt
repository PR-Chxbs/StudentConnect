package com.prince.studentconnect.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun parseTimestamp(sentAt: String, zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return try {
        // Case 1: It's a number (epoch millis)
        sentAt.toLongOrNull()?.let { return it }

        // Case 2: ISO-8601 with offset/zone (e.g. ...Z or ...+02:00)
        return try {
            Instant.parse(sentAt).toEpochMilli()
        } catch (_: Exception) {
            // Case 3: ISO-8601 *without* offset (e.g. 2025-09-29T07:53:31.547684)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val localDateTime = LocalDateTime.parse(sentAt, formatter)
            localDateTime.atZone(zoneId).toInstant().toEpochMilli()
        }
    } catch (e: Exception) {
        // Fallback: return "now"
        Instant.now().toEpochMilli()
    }
}

