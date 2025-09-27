package com.prince.studentconnect.data.remote.fakeapi.fakedata

import com.prince.studentconnect.data.remote.dto.user.Campus
import com.prince.studentconnect.data.remote.dto.user.Course
import com.prince.studentconnect.data.remote.dto.user.GetUserResponse

val sampleUsers = mutableListOf(
    GetUserResponse(
        user_id = "user1",
        first_name = "John",
        last_name = "Doe",
        role = "student",
        bio = "Hiii, I am a 3rd year Logistics and Supply Chain Management student. I am working really hard to earn a cum laude. I look forward to connecting with people who have a strong drive and are eager to succeed.💃",
        campus = Campus(1, "Main Campus"),
        course = Course(1, "Computer Science"),
        profile_picture_url = "https://randomuser.me/api/portraits/men/11.jpg"
    ),
    GetUserResponse(
        user_id = "student_2",
        first_name = "Alice",
        last_name = "Smith",
        role = "student",
        bio = "Second-year Psychology student, passionate about understanding people’s behavior and mental health awareness.🧠",
        campus = Campus(2, "North Campus"),
        course = Course(2, "Psychology"),
        profile_picture_url = "https://randomuser.me/api/portraits/women/12.jpg"
    ),
    GetUserResponse(
        user_id = "student_3",
        first_name = "Bob",
        last_name = "Johnson",
        role = "student",
        bio = "Final year Computer Science student. I love coding hackathons, AI research, and mentoring juniors in programming.👨‍💻",
        campus = Campus(1, "Main Campus"),
        course = Course(1, "Computer Science"),
        profile_picture_url = "https://i.pravatar.cc/150?img=1"
    ),
    GetUserResponse(
        user_id = "lecturer_1",
        first_name = "Dr.",
        last_name = "Brown",
        role = "lecturer",
        bio = "Lecturer in Pure Mathematics with a passion for problem-solving and guiding young mathematicians.📊",
        campus = Campus(1, "Main Campus"),
        course = Course(3, "Mathematics"),
        profile_picture_url = "https://randomuser.me/api/portraits/men/21.jpg"
    ),
    GetUserResponse(
        user_id = "lecturer_2",
        first_name = "Mrs.",
        last_name = "Van Der Merwe",
        role = "lecturer",
        bio = "Senior Lecturer in Business Management. Helping students grow into future entrepreneurs.📈",
        campus = Campus(3, "Business Campus"),
        course = null,
        profile_picture_url = "https://i.pravatar.cc/150?img=5"
    ),
    GetUserResponse(
        user_id = "student_4",
        first_name = "Lerato",
        last_name = "Mokoena",
        role = "student",
        bio = "Third-year Law student. Interested in human rights, constitutional law, and debating.⚖️",
        campus = Campus(4, "Law Campus"),
        course = Course(4, "Law"),
        profile_picture_url = "https://randomuser.me/api/portraits/women/33.jpg"
    ),
    GetUserResponse(
        user_id = "student_5",
        first_name = "Carlos",
        last_name = "Martinez",
        role = "student",
        bio = "First-year Engineering student. Love building robots and playing soccer on weekends.🤖⚽",
        campus = Campus(5, "Engineering Campus"),
        course = Course(5, "Mechanical Engineering"),
        profile_picture_url = "https://randomuser.me/api/portraits/men/45.jpg"
    ),
    GetUserResponse(
        user_id = "student_6",
        first_name = "Amira",
        last_name = "Khan",
        role = "student",
        bio = "Masters student in Data Science. Into machine learning, big data analytics, and startup innovation.📡",
        campus = Campus(1, "Main Campus"),
        course = Course(6, "Data Science"),
        profile_picture_url = "https://i.pravatar.cc/150?img=20"
    ),
    GetUserResponse(
        user_id = "lecturer_3",
        first_name = "Prof.",
        last_name = "Nguyen",
        role = "lecturer",
        bio = "Professor in Software Engineering. Researcher in cloud computing and scalable systems.☁️",
        campus = Campus(1, "Main Campus"),
        course = Course(7, "Software Engineering"),
        profile_picture_url = "https://randomuser.me/api/portraits/men/32.jpg"
    ),
    GetUserResponse(
        user_id = "student_7",
        first_name = "Sibusiso",
        last_name = "Dlamini",
        role = "student",
        bio = "Second-year Accounting student, working part-time as a tutor. Love numbers and teamwork.💼",
        campus = Campus(3, "Business Campus"),
        course = Course(8, "Accounting"),
        profile_picture_url = "https://i.pravatar.cc/150?img=30"
    )
)
