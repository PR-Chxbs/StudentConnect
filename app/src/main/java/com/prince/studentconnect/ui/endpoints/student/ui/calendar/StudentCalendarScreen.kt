package com.prince.studentconnect.ui.endpoints.student.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
        bottomBar = bottomBar
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SwipeableMonthCalendar(
                events = viewModel.eventsForSelectedDate,
                selectedDate = viewModel.selectedDate,
                onDateSelected = viewModel::selectDate,
            )

            Spacer(modifier = Modifier.height(16.dp))

            EventList(
                events = viewModel.eventsForSelectedDate,
                onEventClick = viewModel::onEventClick
            )
        }
    }
}