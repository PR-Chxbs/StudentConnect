package com.prince.studentconnect.ui.components.calendar


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.data.remote.dto.event.Event
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.prince.studentconnect.utils.parseTimestamp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventList(
    events: List<Event>,
    onEventClick: (Event) -> Unit
) {
    Log.d("CalendarScreen", "Events: $events")
    LazyColumn {
        if (events.isEmpty()) {
            item {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No Events Found", style = MaterialTheme.typography.bodyMedium )
                }
            }
        } else {
            items(events) { event ->
                EventItem(event = event, onClick = { onEventClick(event) })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(48.dp)
                .background(
                    color = Color(event.color_code.toColorInt()),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = event.icon_url,
            contentDescription = event.title,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(event.title, modifier = Modifier.weight(1f))

        Text(
            text = formatEpochMillis(event.start_at),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatEpochMillis(timestampString: String, zoneId: ZoneId = ZoneId.systemDefault()): String {
    val epochMillis = parseTimestamp(timestampString)
    val targetDate = Instant.ofEpochMilli(epochMillis)
        .atZone(zoneId)
        .toLocalDate()
    val today = LocalDate.now(zoneId)
    val tomorrow = today.plusDays(1)

    Log.d("EventList", "Timestamp: $timestampString")
    Log.d("EventList", "Event date: $targetDate")

    return when (targetDate) {
        today -> "Today"
        tomorrow -> "Tomorrow"
        else -> targetDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
}