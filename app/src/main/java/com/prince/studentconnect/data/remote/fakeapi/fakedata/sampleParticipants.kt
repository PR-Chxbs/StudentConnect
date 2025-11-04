package com.prince.studentconnect.data.remote.fakeapi.fakedata

import com.prince.studentconnect.data.remote.dto.event.Participant

// Sample participants
val participantsPool = mutableListOf(
    Participant("student_1", "John Doe", "accepted", true),
    Participant("student_2", "Alice Smith", "accepted", false),
    Participant("student_3", "Bob Johnson", "pending", false),
    Participant("lecturer_1", "Dr. Brown", "accepted", false),
    Participant("lecturer_2", "Prof. David Lee", "accepted", false)
)