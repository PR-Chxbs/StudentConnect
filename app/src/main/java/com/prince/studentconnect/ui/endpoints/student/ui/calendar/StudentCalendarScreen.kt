package com.prince.studentconnect.ui.endpoints.student.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.prince.studentconnect.navigation.Screen
import com.prince.studentconnect.ui.components.calendar.EventList
import com.prince.studentconnect.ui.components.calendar.SwipeableMonthCalendar
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCalendarScreen(
    navController: NavController,
    bottomBar: @Composable () -> Unit,
    viewModel: CalendarViewModel
) {
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* handle click */ },
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                ),
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (!isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SwipeableMonthCalendar(
                eventsByDate = viewModel.eventsByDate,
                selectedDate = viewModel.selectedDate,
                onDateSelected = viewModel::selectDate,
                modifier = Modifier.padding(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            EventList(
                events = viewModel.eventsForSelectedDate,
                onEventClick = { eventId -> navController.navigate(Screen.StudentEventDetails.route.replace("{event_id}", "$eventId"))}
            )
        }
    }
}