package com.prince.studentconnect.ui.components.shared

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerRow(
    label: String,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current

    // Remember the dialog so it doesn't get recreated on recomposition
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                onTimeSelected(LocalTime.of(hour, minute))
            },
            LocalTime.now().hour,
            LocalTime.now().minute,
            true // 24-hour format, set false if you want AM/PM
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { timePickerDialog.show() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Text(
                text = selectedTime?.toString() ?: "Select time",
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedTime != null)
                    MaterialTheme.colorScheme.onBackground
                else
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }

        IconButton(onClick = { timePickerDialog.show() }) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Pick time",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
