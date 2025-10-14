package com.prince.studentconnect.ui.endpoints.student.model.chat

import android.os.Build
import androidx.annotation.RequiresApi
import com.prince.studentconnect.data.remote.dto.conversation.Message
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.prince.studentconnect.data.remote.fakeapi.FakeConversationApi.InternalMessage
import com.prince.studentconnect.utils.parseTimestamp
import java.time.Instant

data class MessageUiModel(
    val id: Int,
    val senderId: String,
    val text: String,
    val attachmentUrl: String?,
    val attachmentType: String?,
    val sentAtTimestamp: String,
    val sentAtEpoch: Long,
    val isMine: Boolean // derived from comparing senderId with currentUserId
)

@RequiresApi(Build.VERSION_CODES.O)
fun SendMessageResponse.toUiModel(currentUserId: String): MessageUiModel {
    return MessageUiModel(
        id = message_id,
        senderId = sender_id,
        text = message_text,
        attachmentUrl = attachment_url,
        attachmentType = attachment_type,
        sentAtTimestamp = sent_at,
        sentAtEpoch = parseTimestamp(sent_at),
        isMine = sender_id == currentUserId
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Message.toUiModel(currentUserId: String): MessageUiModel {
    return MessageUiModel(
        id = message_id,
        senderId = sender_id,
        text = message_text,
        attachmentUrl = attachment_url,
        attachmentType = null,
        sentAtTimestamp = sent_at,
        sentAtEpoch = parseTimestamp(sent_at),
        isMine = sender_id == currentUserId
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun InternalMessage.toUiModel(currentUserId: String): MessageUiModel {
    return MessageUiModel(
        id = message_id,
        senderId = sender_id,
        text = message_text,
        attachmentUrl = attachment_url,
        attachmentType = attachment_type,
        sentAtTimestamp = sent_at,
        sentAtEpoch = parseTimestamp(sent_at),
        isMine = sender_id == currentUserId
    )
}
