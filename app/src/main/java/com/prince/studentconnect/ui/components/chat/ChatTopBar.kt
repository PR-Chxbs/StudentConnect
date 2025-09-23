package com.prince.studentconnect.ui.components.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatTopBar(
    viewModel: MessageViewModel,
    onBackClick: () -> Unit
) {
    val title = if (viewModel.groupProfileImages.isNotEmpty()) {
        "" // will show images for group
    } else {
        viewModel.members.firstOrNull { it.userId != viewModel.userId }?.firstName ?: "User"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(0.5.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        if (viewModel.groupProfileImages.isNotEmpty()) {
            GroupProfileImages(images = viewModel.groupProfileImages)
        } else {
            Image(
                painter = rememberAsyncImagePainter(viewModel.otherUserProfile),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = title, style = MaterialTheme.typography.titleMedium)
    }
}
