package com.prince.studentconnect.ui.components.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.times
import coil.compose.rememberAsyncImagePainter

@Composable
fun GroupProfileImages(images: List<String>) {
    val displayedImages = images.take(3)
    val imageSize = 32.dp
    val overlapOffset = (-12).dp

    Row {
        displayedImages.forEachIndexed { index, url ->
            Box(
                modifier = Modifier
                    .offset(x = index * overlapOffset) // offset applied to container
            ) {
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                )
            }
        }
    }
}

