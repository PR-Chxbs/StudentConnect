package com.prince.studentconnect.ui.components.shared

import android.app.DatePickerDialog
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
import androidx.compose.material.icons.filled.DateRange
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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerRow(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current

    // Remember the dialog so it doesn't get recreated on recomposition
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { datePickerDialog.show() }
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
                text = selectedDate?.toString() ?: "Select date",
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedDate != null)
                    MaterialTheme.colorScheme.onBackground
                else
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }

        IconButton(onClick = { datePickerDialog.show() }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Pick date",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}