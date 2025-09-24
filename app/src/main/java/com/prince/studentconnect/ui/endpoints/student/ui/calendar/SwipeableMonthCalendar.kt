package com.prince.studentconnect.ui.endpoints.student.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prince.studentconnect.data.remote.dto.event.Event
import com.prince.studentconnect.R
import java.time.*
import java.time.format.TextStyle
import java.util.*
import androidx.core.graphics.toColorInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableMonthCalendar(
    events: List<Event>,
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate? = null,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val initialMonth = YearMonth.now()
    val totalRange = 240 // 10 years (-120..+120)
    val startMonth = initialMonth.minusMonths((totalRange / 2).toLong())

    val pagerState = rememberPagerState(initialPage = totalRange / 2, pageCount = { totalRange })

    val currentMonth = remember(pagerState.currentPage) {
        startMonth.plusMonths(pagerState.currentPage.toLong())
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Top month label
        Text(
            text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                    " " + currentMonth.year,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Weekday headers (Sunday first → Saturday last)
        Row(modifier = Modifier.fillMaxWidth()) {
            val daysOfWeek = DayOfWeek.values().toList()
            val reordered = daysOfWeek.drop(6) + daysOfWeek.dropLast(1) // Sunday first
            reordered.forEach { day ->
                val isWeekend = day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY
                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()).first().toString(), // .first().toString() to abbreviated name e.g. Sun, Mon
                    modifier = Modifier.weight(1f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = if (isWeekend) Color.Red else MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Swipeable months
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Start)
        ) { page ->
            val month = startMonth.plusMonths(page.toLong())
            MonthGrid(
                month = month,
                today = today,
                selectedDate = selectedDate,
                events = events,
                onDateSelected = onDateSelected
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthGrid(
    month: YearMonth,
    today: LocalDate,
    selectedDate: LocalDate?,
    events: List<Event>,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = month.atDay(1)
    val lastDayOfMonth = month.atEndOfMonth()
    val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7) // Sunday=0
    val daysInMonth = lastDayOfMonth.dayOfMonth

    val totalCells = ((firstDayOfWeek + daysInMonth + 6) / 7) * 7
    val startDate = firstDayOfMonth.minusDays(firstDayOfWeek.toLong())
    val days = (0 until totalCells).map { startDate.plusDays(it.toLong()) }

    Column {
        days.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    val isToday = date == today
                    val isSelected = date == selectedDate
                    val isCurrentMonth = date.month == month.month
                    val isWeekend = date.dayOfWeek == DayOfWeek.SUNDAY || date.dayOfWeek == DayOfWeek.SATURDAY

                    // Find events for this day
                    val dayEvents = events.filter {
                        val eventDate = LocalDate.parse(it.start_at.substring(0, 10)) // assumes ISO-8601
                        eventDate == date
                    }
                    val underlineColor = dayEvents.lastOrNull()?.color_code

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clickable { onDateSelected(date) }
                            .border(1.dp,
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.surfaceVariant
                                    else -> Color.Transparent
                                }),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                color = when {
                                    isToday -> MaterialTheme.colorScheme.onPrimary
                                    !isCurrentMonth -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    isWeekend -> Color.Red
                                    else -> MaterialTheme.colorScheme.onBackground
                                },
                                fontWeight = FontWeight.Normal, // if (isToday) FontWeight.Bold else FontWeight.Normal
                                modifier = Modifier
                                    .background(
                                        color = when {
                                            isToday -> MaterialTheme.colorScheme.primary
                                            else -> Color.Transparent
                                        },
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(8.dp),
                                fontSize = 12.sp

                            )

                            // Event underline
                            if (underlineColor != null) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 2.dp)
                                        .height(3.dp)
                                        .width(20.dp)
                                        .background(
                                            color = Color(underlineColor.toColorInt()),
                                            shape = RoundedCornerShape(50)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

