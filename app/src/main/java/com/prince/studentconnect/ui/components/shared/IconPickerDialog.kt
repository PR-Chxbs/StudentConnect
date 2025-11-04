package com.prince.studentconnect.ui.components.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip

@Composable
fun IconPickerDialog(
    onDismissRequest: () -> Unit,
    onIconSelected: (String) -> Unit
) {
    // Pre-defined event icons (URLs)
    val icons = listOf(
        "https://img.icons8.com/fluency/48/000000/laptop.png",      // Lecture
        "https://img.icons8.com/fluency/48/000000/teamwork.png",     // Meeting
        "https://img.icons8.com/fluency/48/000000/test-tube.png", // Appointment
        "https://img.icons8.com/fluency/48/000000/home.png",        // Personal
        "https://img.icons8.com/fluency/48/000000/book.png",      // Sports
        "https://img.icons8.com/fluency/48/000000/music.png",       // Music
        "https://img.icons8.com/fluency/48/000000/birthday.png",    // Birthday
        "https://img.icons8.com/fluency/48/000000/coffee.png"       // Casual meetup
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Pick an Icon") },
        text = {
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(icons) { iconUrl ->
                        AsyncImage(
                            model = iconUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onIconSelected(iconUrl)
                                    onDismissRequest()
                                }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
