package com.prince.studentconnect.ui.endpoints.student.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel
import com.prince.studentconnect.data.remote.dto.event.CreateEventRequest
import java.time.LocalDateTime
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.prince.studentconnect.ui.components.shared.ColorPickerDialog
import com.prince.studentconnect.ui.components.shared.DatePickerRow
import com.prince.studentconnect.ui.components.shared.IconPickerDialog
import com.prince.studentconnect.ui.components.shared.RecurrenceDropdown
import com.prince.studentconnect.ui.components.shared.TimePickerRow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.prince.studentconnect.R


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEventScreen(
    navController: NavController,
    viewModel: CalendarViewModel,
    currentUserId: String
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedRecurrence by remember { mutableStateOf("NONE") }
    var reminder by remember { mutableStateOf(LocalDateTime.now().plusHours(2)) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    val listState = rememberLazyListState()
    val imePadding = WindowInsets.ime.asPaddingValues()

    var showIconPicker by remember { mutableStateOf(false) }
    var selectedIcon by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(imePadding.calculateBottomPadding()) {
        listState.animateScrollToItem(6)
    }

    var showColorPicker by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf<String?>(null) }

    if (showColorPicker) {
        ColorPickerDialog(
            onDismissRequest = { showColorPicker = false },
            onColorSelected = { colorHex ->
                selectedColor = colorHex
                showColorPicker = false
            }
        )
    }

    val eventCreatedText = stringResource(R.string.event_created)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_event)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (title.isBlank() || description.isBlank() || selectedIcon.isNullOrBlank() || selectedColor.isNullOrBlank()) {
                        Toast.makeText(context, "Please enter missing fields", Toast.LENGTH_LONG).show()
                    } else {
                        val selectedDateTime: LocalDateTime = LocalDateTime.of(selectedDate, selectedTime)
                        val request = CreateEventRequest(
                            creator_id = currentUserId, // replace with real user id
                            conversation_id = null,
                            title = title,
                            description = description,
                            start_at = selectedDateTime.format(DateTimeFormatter.ISO_DATE_TIME),
                            icon_url = selectedIcon ?:"https://img.icons8.com/fluency/48/000000/laptop.png",
                            color_code = selectedColor ?: "#4CAF50",
                            recurrence_rule = selectedRecurrence,
                            reminder_at = reminder.format(DateTimeFormatter.ISO_DATE_TIME)
                        )
                        viewModel.addEvent(
                            request,
                            onSuccess = {
                                Toast.makeText(context, eventCreatedText, Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)/*
                .verticalScroll(rememberScrollState())
                .imePadding()*/,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.tittle)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                DatePickerRow(
                    label = "Start At",
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
            }

            item {
                TimePickerRow(
                    label = "Start Time",
                    selectedTime = selectedTime,
                    onTimeSelected = { selectedTime = it }
                )
            }

            item {
                RecurrenceDropdown(
                    selectedRule = selectedRecurrence,
                    onRuleSelected = { selectedRecurrence = it }
                )
            }

            item {
                if (showIconPicker) {
                    IconPickerDialog(
                        onDismissRequest = { showIconPicker = false },
                        onIconSelected = { iconUrl ->
                            selectedIcon = iconUrl
                            showIconPicker = false
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showIconPicker = true }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(stringResource(R.string.event_icon), style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = selectedIcon?.substringAfterLast("/") ?: "Pick an icon",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    AsyncImage(
                        model = selectedIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedIcon.isNullOrBlank()) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f) else Color.Transparent
                            )
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showColorPicker = true }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(stringResource(R.string.event_color), style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = selectedColor ?: "Pick a color",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                selectedColor?.let { Color(it.toColorInt()) }
                                    ?: MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                            )
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .padding(imePadding)
                )
            }
        }
    }
}
