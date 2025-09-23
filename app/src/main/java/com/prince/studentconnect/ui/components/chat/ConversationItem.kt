package com.prince.studentconnect.ui.components.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.prince.studentconnect.ui.endpoints.student.model.chat.ConversationUiModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ConversationItem(
    conversation: ConversationUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background // overrides default
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- Profile Image(s) ---
            if (conversation.profileImages.size == 1) {
                Image(
                    painter = rememberAsyncImagePainter(conversation.profileImages.first()),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            } else {
                // Show up to 3 small stacked images for group/module
                Box {
                    conversation.profileImages.take(3).forEachIndexed { index, url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = "Member",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .offset(x = (index * 20).dp)
                        )
                    }
                }
            }

            // --- Conversation Info ---
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = conversation.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = conversation.latestMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // --- Timestamp + Unread Indicator ---
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                // Format timestamp safely
                val timeFormatted = try {
                    val instant = kotlinx.datetime.Instant.parse(conversation.latestMessageTimestamp)
                    val time = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                    String.format("%02d:%02d", time.hour, time.minute)
                } catch (e: Exception) {
                    conversation.latestMessageTimestamp.take(5)
                }

                Text(
                    text = timeFormatted,
                    style = MaterialTheme.typography.bodySmall
                )

                if (conversation.unreadCount > 0) {
                    Badge(
                        modifier = Modifier.padding(top = 4.dp),
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Text("${conversation.unreadCount}")
                    }
                }
            }
        }
    }
}

