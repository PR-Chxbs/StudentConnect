package com.prince.studentconnect.data.remote.fakeapi.fakedata

import com.prince.studentconnect.data.remote.fakeapi.FakeConversationApi.InternalConversation
import com.prince.studentconnect.data.remote.fakeapi.FakeConversationApi.InternalMember

var nextConversationId = 1

val sampleConversations = mutableListOf(

    // ----- Students -----
    InternalConversation(
        conversation_id = nextConversationId++,
        name = "",
        type = "private_student",
        max_members = 2,
        members = mutableListOf(
            InternalMember(
                user_id = "student_2",
                first_name = "Alice",
                last_name = "Smith",
                profile_picture_url = "https://randomuser.me/api/portraits/women/12.jpg",
                joined_at = "2025-09-21T08:05:00Z"
            ),
            InternalMember(
                user_id = "student_1",
                first_name = "John",
                last_name = "Doe",
                profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg",
                joined_at = "2025-09-22T08:10:00Z"
            )
        ),
        date_created = "2025-09-22T08:00:00Z",
        messages = messagesOne
    ),

    InternalConversation(
        conversation_id = nextConversationId++,
        name = "",
        type = "private_student",
        max_members = 2,
        members = mutableListOf(
            InternalMember(
                user_id = "student_3",
                first_name = "Bob",
                last_name = "Johnson",
                profile_picture_url = "https://i.pravatar.cc/150?img=1",
                joined_at = "2025-09-22T08:00:00Z"
            ),
            InternalMember(
                user_id = "student_1",
                first_name = "John",
                last_name = "Doe",
                profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg",
                joined_at = "2025-09-22T08:10:00Z"
            )
        ),
        date_created = "2025-09-22T08:00:00Z",
        messages = messagesTwo
    ),

    // ----- Lecturers -----

    InternalConversation(
        conversation_id = nextConversationId++,
        name = "",
        type = "private_lecturer",
        max_members = 2,
        members = mutableListOf(
            InternalMember(
                user_id = "lecturer_1",
                first_name = "Dr.",
                last_name = "Brown",
                profile_picture_url = "https://randomuser.me/api/portraits/men/21.jpg",
                joined_at = "2025-06-20T10:00:00Z"
            ),
            InternalMember(
                user_id = "student_1",
                first_name = "John",
                last_name = "Doe",
                profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg",
                joined_at = "2025-06-20T10:05:00Z"
            )
        ),
        date_created = "2025-06-20T10:00:00Z",
        messages = messagesThree
    ),


    // ----- Groups -----
    InternalConversation(
        conversation_id = nextConversationId++,
        name = "Math Study Group",
        type = "group",
        max_members = 5,
        members = mutableListOf(
            InternalMember(
                user_id = "student_1",
                first_name = "John",
                last_name = "Doe",
                profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg",
                joined_at = "2025-09-21T08:00:00Z"
            ),
            InternalMember(
                user_id = "student_2",
                first_name = "Alice",
                last_name = "Smith",
                profile_picture_url = "https://randomuser.me/api/portraits/women/12.jpg",
                joined_at = "2025-09-21T08:05:00Z"
            ),
            InternalMember(
                user_id = "student_3",
                first_name = "Bob",
                last_name = "Johnson",
                profile_picture_url = "https://i.pravatar.cc/150?img=1",
                joined_at = "2025-09-21T08:10:00Z"
            )
        ),
        date_created = "2025-09-21T08:00:00Z",
        messages = messagesFour
    ),

    // ----- Module Default (also under Groups tab) -----

    InternalConversation(
        conversation_id = nextConversationId++,
        name = "Physics 101",
        type = "module_default",
        max_members = 20,
        members = mutableListOf(
            InternalMember(
                user_id = "student_1",
                first_name = "John",
                last_name = "Doe",
                profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg",
                joined_at = "2025-08-21T08:00:00Z"
            ),
            InternalMember(
                user_id = "student_2",
                first_name = "Alice",
                last_name = "Smith",
                profile_picture_url = "https://randomuser.me/api/portraits/women/12.jpg",
                joined_at = "2025-08-21T08:05:00Z"
            ),
            InternalMember(
                user_id = "student_3",
                first_name = "Bob",
                last_name = "Johnson",
                profile_picture_url = "https://i.pravatar.cc/150?img=1",
                joined_at = "2025-08-21T08:10:00Z"
            ),
            InternalMember(
                user_id = "lecturer_1",
                first_name = "Dr.",
                last_name = "Brown",
                profile_picture_url = "https://randomuser.me/api/portraits/men/21.jpg",
                joined_at = "2025-08-20T10:00:00Z"
            ),
            InternalMember(
                user_id = "lecturer_2",
                first_name = "Mrs.",
                last_name = "Van Der Merwe",
                profile_picture_url = "https://i.pravatar.cc/150?img=5",
                joined_at = "2025-08-20T10:00:00Z"
            ),
        ),
        date_created = "2025-08-19T08:00:00Z",
        messages = messagesFive
    )
)

