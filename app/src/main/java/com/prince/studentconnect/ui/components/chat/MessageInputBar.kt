package com.prince.studentconnect.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    val showSend = text.isNotBlank()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(50))
            .border(
                width = if (!isSystemInDarkTheme()) 1.dp else 0.dp,
                shape = RoundedCornerShape(50),
                color = if (!isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else Color.Transparent
            )
            .padding(horizontal = 12.dp, vertical = 0.dp), // smaller vertical padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences, // 👈 Capitalize first letter
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (showSend) {
                        onSend()
                    }
                }
            ),
            modifier = Modifier
                .weight(1f), // fixed height for smaller bar
            placeholder = { Text("Type a message...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor  = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            trailingIcon = {
                if (showSend) {
                    IconButton(
                        onClick = onSend,
                        modifier = Modifier.size(24.dp) // slightly smaller
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        )

        if (!showSend) {
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = { /* Attach action */ }, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.AttachFile, contentDescription = "Attach")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /* Camera action */ }, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}



/*
@Preview
@Composable
fun previewMessageInputBar() {
    MessageInputBar(
        text = "Enter Message",

    )
}*/