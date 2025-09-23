package com.prince.studentconnect.ui.components.chat

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.prince.studentconnect.ui.endpoints.student.model.chat.MessageUiModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun MessagesList(
    messages: List<MessageUiModel>,
    listState: LazyListState,
    currentUserId: String
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        reverseLayout = false
    ) {
        if (messages.isEmpty()) return@LazyColumn

        var lastMessageDay: String? = null

        messages.forEach { message ->
            val messageDay = formatDateSeparator(message.sentAtEpoch)
            if (messageDay != lastMessageDay) {
                item { DateSeparator(dateText = messageDay) }
                lastMessageDay = messageDay
            }

            item { MessageBubble(message = message) }
        }
    }
}


@Composable
fun MessageBubble(message: MessageUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isMine) {
            Image(
                painter = rememberAsyncImagePainter(message.senderId), // replace with actual profile url
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    if (message.isMine) MaterialTheme.colorScheme.primary else Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
                .widthIn(max = 250.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    color = if (message.isMine) Color.White else Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        Date(message.sentAtEpoch)
                    ),
                    fontSize = 10.sp,
                    color = if (message.isMine) Color.White.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

fun formatDateSeparator(epochMillis: Long): String {
    val messageDate = Calendar.getInstance().apply { timeInMillis = epochMillis }
    val today = Calendar.getInstance()

    return when {
        messageDate.get(Calendar.YEAR) != today.get(Calendar.YEAR) ||
                messageDate.get(Calendar.DAY_OF_YEAR) != today.get(Calendar.DAY_OF_YEAR) -> {
            val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
            when {
                messageDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> "Yesterday"
                else -> SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(Date(epochMillis))
            }
        }
        else -> "Today"
    }
}