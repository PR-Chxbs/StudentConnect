package com.prince.studentconnect.ui.components.module

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse

@Composable
fun ModuleCard(
    module: GetModulesResponse,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    isAdminMode: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = module.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Code: ${module.code}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = module.description, style = MaterialTheme.typography.bodySmall)

            if (isAdminMode) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onEditClick(module.module_id) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Edit")
                    }

                    OutlinedButton(
                        onClick = { onDeleteClick(module.module_id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
