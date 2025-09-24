package com.prince.studentconnect.data.remote.fakeapi.fakedata

import android.os.Build
import androidx.annotation.RequiresApi
import com.prince.studentconnect.data.remote.dto.event.Participant
import com.prince.studentconnect.data.remote.fakeapi.FakeEventApi
import com.prince.studentconnect.data.remote.fakeapi.InternalEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private var event_id = 1

// Sample events
@RequiresApi(Build.VERSION_CODES.O)
val sampleEvents = mutableListOf(
    InternalEvent(
        event_id = event_id++,
        creator_id = "student_1",
        conversation_id = null,
        title = "Math Study Group",
        description = "Calculus revision session",
        icon_url = "https://img.icons8.com/fluency/48/000000/book.png",
        color_code = "#FF5722",
        start_at = LocalDateTime.now().plusDays(0).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "NONE",
        reminder_at = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[0], participantsPool[1])
    ),
    InternalEvent(
        event_id = event_id++,
        creator_id = "student_1",
        conversation_id = null,
        title = "Math Study Group",
        description = "Calculus revision session",
        icon_url = "https://img.icons8.com/fluency/48/000000/book.png",
        color_code = "#FF5722",
        start_at = LocalDateTime.now().plusDays(0).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "NONE",
        reminder_at = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[0], participantsPool[1])
    ),
    InternalEvent(
        event_id = event_id++,
        creator_id = "lecturer_1",
        conversation_id = 1,
        title = "Computer Science Lecture",
        description = "Intro to Algorithms",
        icon_url = "https://img.icons8.com/fluency/48/000000/laptop.png",
        color_code = "#4CAF50",
        start_at = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "WEEKLY",
        reminder_at = LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[3], participantsPool[2])
    ),
    InternalEvent(
        event_id = event_id++,
        creator_id = "student_2",
        conversation_id = 2,
        title = "Physics Lab",
        description = "Experiment on optics",
        icon_url = "https://img.icons8.com/fluency/48/000000/lab.png",
        color_code = "#2196F3",
        start_at = LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "NONE",
        reminder_at = LocalDateTime.now().plusHours(3).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[1], participantsPool[4])
    ),
    InternalEvent(
        event_id = event_id++,
        creator_id = "lecturer_2",
        conversation_id = 3,
        title = "Chemistry Seminar",
        description = "Organic Chemistry Q&A",
        icon_url = "https://img.icons8.com/fluency/48/000000/test-tube.png",
        color_code = "#9C27B0",
        start_at = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "MONTHLY",
        reminder_at = LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[0], participantsPool[3])
    ),
    InternalEvent(
        event_id = event_id++,
        creator_id = "student_3",
        conversation_id = null,
        title = "Group Project Meeting",
        description = "Discuss app UI",
        icon_url = "https://img.icons8.com/fluency/48/000000/teamwork.png",
        color_code = "#FFC107",
        start_at = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ISO_DATE_TIME),
        recurrence_rule = "NONE",
        reminder_at = LocalDateTime.now().plusHours(5).format(DateTimeFormatter.ISO_DATE_TIME),
        created_at = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
        participants = mutableListOf(participantsPool[2], participantsPool[1])
    )
)
