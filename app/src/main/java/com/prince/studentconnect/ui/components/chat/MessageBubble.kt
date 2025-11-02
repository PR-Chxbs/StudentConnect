package com.prince.studentconnect.ui.components.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.prince.studentconnect.navigation.Screen
import com.prince.studentconnect.ui.endpoints.student.model.chat.MessageUiModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessagesList(
    messages: List<MessageUiModel>,
    listState: LazyListState,
    viewModel: MessageViewModel,
    currentUserId: String
) {
    // compute chat items only when messages list changes
    val chatItems = remember(messages) {
        buildChatItemsWithDebug(messages)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        reverseLayout = false
    ) {
        if (chatItems.isEmpty()) {
            item {
                Text(
                    text = "No messages yet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            return@LazyColumn
        }

        var isFirstRun = true

        items(chatItems) { chatItem ->
            when (chatItem) {
                is ChatItem.DateSeparatorItem -> {
                    // DateSeparator composable (keeps same appearance)
                    DateSeparator(dateText = chatItem.label)
                }

                is ChatItem.MessageItem -> {
                    // optional small spacing when profile is shown (mimic your old spacing)
                    if (chatItem.showProfile && !isFirstRun) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // Render MessageBubble with the precomputed showProfile flag
                    MessageBubble(
                        message = chatItem.message,
                        viewModel = viewModel,
                        displayProfile = chatItem.showProfile
                    )
                }
            }
            if (isFirstRun) isFirstRun = false
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBubble(
    message: MessageUiModel,
    viewModel: MessageViewModel,
    displayProfile: Boolean,
) {
    val nameColorsList = listOf(
        "#708090", // SlateGrey
        "#778899", // LightSlateGray
        "#FF00FF", // Magenta
        "#808080", // Gray
        "#FF4500", // OrangeRed
        "#9370DB", // MediumPurple
        "#7B68EE", // MediumSlateBlue
        "#1E90FF", // DodgerBlue
        "#4682B4", // SteelBlue
        "#228B22", // ForestGreen
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!message.isMine) {
            val senderProfileUrl =
                if (displayProfile)
                    viewModel.members
                    .firstOrNull { it.userId == message.senderId } // match the sender of this message
                    ?.profilePictureUrl
                    ?: "https://randomuser.me/api/portraits/men/11.jpg" // fallback
                else
                    ""

            Image(
                painter = rememberAsyncImagePainter(senderProfileUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    if (message.isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = if (!isSystemInDarkTheme() && !message.isMine) 1.dp else 0.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = if (!isSystemInDarkTheme() && !message.isMine) MaterialTheme.colorScheme.outline else Color.Transparent
                )
                .padding(top = 8.dp, bottom = 1.dp, start = 8.dp, end = 8.dp)
                .widthIn(max = 250.dp)
        ) {
            Column {
                // Sender name
                if (viewModel.isGroupConversation && !message.isMine && displayProfile) {
                    val sender = viewModel.members.firstOrNull {it.userId == message.senderId}
                    var colorIndex = viewModel.members.indexOf(sender)
                    if (colorIndex + 1 > nameColorsList.size)
                        colorIndex = Random.nextInt(0, nameColorsList.size)

                    Text(
                        text = if (sender != null) "${sender.firstName} ${sender.lastName}" else "User",
                        color = Color(nameColorsList[colorIndex].toColorInt()),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {viewModel.navController.navigate(Screen.StudentProfile.route.replace("{user_id}", message.senderId))}
                    )
                }

                // Message text
                Text(
                    text = message.text,
                    color = if (message.isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Timestamp
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        Date(message.sentAtEpoch)
                    ),
                    fontSize = 10.sp,
                    color = if (message.isMine) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
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

sealed class ChatItem {
    data class DateSeparatorItem(val label: String, val epochForLabel: Long) : ChatItem()
    data class MessageItem(val message: MessageUiModel, val showProfile: Boolean) : ChatItem()
}

fun buildChatItemsWithDebug(
    messages: List<MessageUiModel>
): List<ChatItem> {
    val items = mutableListOf<ChatItem>()

    var lastMessageDate = ""
    var previousSenderId: String? = null

    // assume messages are already chronological (old -> new). If not, sort by epoch:
    val ordered = messages.sortedBy { it.sentAtEpoch }

    for ((index, msg) in ordered.withIndex()) {
        // derive the LocalDate of the message in the chosen timezone

        val messageDayLabel = formatDateSeparator(msg.sentAtEpoch)
        val isSameDayDate = (messageDayLabel == lastMessageDate)

        // debug string (mirrors your previous output)
        var debugString = "---------------------------\n" +
                "Message: ${msg.text}\n" +
                "Timestamp: ${msg.sentAtTimestamp}\n" +
                "Day: $messageDayLabel\n" +
                "LastMessageDay: $lastMessageDate\n" +
                "IsSameDayDate: $isSameDayDate\n" +
                "Use separator: ${!isSameDayDate}\n"

        // Should we insert a separator?
        var separatorUsed = false
        if (!isSameDayDate) {
            // Insert separator BEFORE this message
            items.add(ChatItem.DateSeparatorItem(label = messageDayLabel, epochForLabel = msg.sentAtEpoch))
            separatorUsed = true
            // update lastMessageDate
            lastMessageDate = messageDayLabel
        }

        // determine whether to show profile image: hide when previous sender is same AND same day
        val isRepeatSender = previousSenderId != null && previousSenderId == msg.senderId && isSameDayDate
        val displayProfile = !isRepeatSender

        // add message item (with precomputed displayProfile)
        items.add(ChatItem.MessageItem(message = msg, showProfile = displayProfile))

        // log debug, consistent with your format
        debugString += "Added separator: $separatorUsed\n" +
                "ShowProfile: $displayProfile\n" +
                "---------------------------\n\n"
        // Log.d("MessageBubble", debugString)

        // update previousSenderId for next iteration
        previousSenderId = msg.senderId
    }

    return items
}