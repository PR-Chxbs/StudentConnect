package com.prince.studentconnect.data.remote.fakeapi.fakedata

import com.prince.studentconnect.data.remote.fakeapi.FakeConversationApi.InternalMessage

var nextMessageId = 1

val messagesOne = mutableListOf(
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Hey, are you ready for the test?",
        sent_at = "2025-09-22T08:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Hey! Almost… still reviewing the last few chapters.",
        sent_at = "2025-09-22T08:03:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Yeah, same here. I think chapter 5 is going to be tricky.",
        sent_at = "2025-09-22T08:07:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Agreed. Want to do a quick revision together this afternoon?",
        sent_at = "2025-09-22T08:12:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Sure! How about 4 pm at the library?",
        sent_at = "2025-09-22T08:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Perfect, see you then!",
        sent_at = "2025-09-22T08:18:00Z"
    )
)

val messagesTwo = mutableListOf(
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Hey, are you ready for the test?",
        sent_at = "2025-09-22T08:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Hey! Kind of nervous 😅 Have you reviewed chapter 3?",
        sent_at = "2025-09-22T08:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Yeah, I went through it last night. The formulas are tricky though.",
        sent_at = "2025-09-22T08:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "True. I might need to do a few practice questions before the test.",
        sent_at = "2025-09-22T08:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Same here. Want to do a quick revision together later?",
        sent_at = "2025-09-22T08:20:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Sounds good! Let’s meet at 5 pm in the library.",
        sent_at = "2025-09-22T08:25:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Perfect, see you then.",
        sent_at = "2025-09-22T08:30:00Z"
    )
)

val messagesThree = mutableListOf(
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Please submit your assignment by Friday.",
        sent_at = "2025-09-21T12:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Got it, Dr. Brown. I’ll work on it tonight.",
        sent_at = "2025-09-21T12:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Quick question: for question 3, should we use the formula from lecture 5?",
        sent_at = "2025-09-21T12:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Yes, that’s the one. Make sure you show your steps clearly.",
        sent_at = "2025-09-21T12:20:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Thanks! That helps.",
        sent_at = "2025-09-21T12:25:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I’m struggling a bit with the integration in question 4 though...",
        sent_at = "2025-09-21T14:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Take a look at the example from last week. That should guide you.",
        sent_at = "2025-09-21T14:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Ah yes, I see it now. Thank you.",
        sent_at = "2025-09-21T14:20:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "No problem. Also, remember to reference the textbook for the derivation.",
        sent_at = "2025-09-21T15:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Will do. I’ll send you a draft tonight.",
        sent_at = "2025-09-21T15:10:00Z"
    ),
    // --- Next day ---
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Good morning, Dr. Brown. I finished questions 1-3. Could you quickly check?",
        sent_at = "2025-09-22T08:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Morning, John. Send them over, I’ll review in an hour.",
        sent_at = "2025-09-22T08:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Sent! Thanks.",
        sent_at = "2025-09-22T08:50:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Looks good overall. Small note on question 2: you can simplify that step.",
        sent_at = "2025-09-22T09:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Ah yes, I see now. Fixing it.",
        sent_at = "2025-09-22T10:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Perfect. Remember to follow the same approach for question 4.",
        sent_at = "2025-09-22T10:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Got it. Will do!",
        sent_at = "2025-09-22T10:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "By the way, will there be any bonus exercises this week?",
        sent_at = "2025-09-22T11:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Yes, a few extra problems for practice. I’ll upload them tonight.",
        sent_at = "2025-09-22T11:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Thanks, that’s great!",
        sent_at = "2025-09-22T11:20:00Z"
    ),
    // --- Random late night ---
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Working on question 4 now. That integration is tricky 😅",
        sent_at = "2025-09-22T23:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Take your time. Focus on breaking it into smaller parts.",
        sent_at = "2025-09-22T23:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Ah yes, that works better. Thank you!",
        sent_at = "2025-09-22T23:30:00Z"
    ),
    // --- Earlier in year examples ---
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Reminder: midterm next week. Start revising early.",
        sent_at = "2025-08-15T09:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Thanks Dr. Brown, will do.",
        sent_at = "2025-08-15T09:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Also, check the posted solutions for previous assignments.",
        sent_at = "2025-08-15T09:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Yes, I’ll review them tonight.",
        sent_at = "2025-08-15T09:15:00Z"
    ),
    // --- More casual chat ---
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Dr. Brown, do you have any tips for the practicals?",
        sent_at = "2025-08-20T13:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Make sure to follow the safety guidelines and review the procedure before coming.",
        sent_at = "2025-08-20T13:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Got it, will do that.",
        sent_at = "2025-08-20T13:50:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Good. That’s all for today. Keep practicing the theory problems.",
        sent_at = "2025-08-20T14:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Thanks Dr. Brown, have a great evening!",
        sent_at = "2025-08-20T14:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Starting the revision now, wish me luck!",
        sent_at = "2025-07-10T18:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Good luck, John! Remember to focus on understanding concepts rather than memorizing.",
        sent_at = "2025-07-10T18:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Thanks, I’ll keep that in mind.",
        sent_at = "2025-07-10T18:15:00Z"
    ),
    // --- Add filler messages to exceed 50 ---
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Reviewing question 2 again, it's tricky.",
        sent_at = "2025-06-25T20:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Break it down step by step, that usually helps.",
        sent_at = "2025-06-25T20:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Ah, that cleared it up! Thanks!",
        sent_at = "2025-06-25T20:20:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I think I’m ready to submit the draft now.",
        sent_at = "2025-06-30T17:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Great! Send it over when done.",
        sent_at = "2025-06-30T17:10:00Z"
    )
)

val messagesFour = mutableListOf(
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Guys, when are we starting our math session?",
        sent_at = "2025-09-21T09:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I’m free after lunch today. How about 2pm?",
        sent_at = "2025-09-21T11:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "2pm works for me, but can we do it online? I don’t feel like going to campus 😅",
        sent_at = "2025-09-21T11:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Haha lazy Bob! Online is fine, I’ll start a Google Meet.",
        sent_at = "2025-09-21T12:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Cool, drop the link here when ready.",
        sent_at = "2025-09-21T12:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Here’s the link 👉 meet.google.com/xyz-math",
        sent_at = "2025-09-21T13:55:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Joining now!",
        sent_at = "2025-09-21T13:58:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Me too. Let’s smash this calculus 😎",
        sent_at = "2025-09-21T14:00:00Z"
    ),
    // Later in the evening
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Thanks guys, that session helped a lot. I actually get integration now!",
        sent_at = "2025-09-21T20:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Same here, I don’t hate math as much anymore 😂",
        sent_at = "2025-09-21T20:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Lol don’t speak too soon, next week is differential equations 😬",
        sent_at = "2025-09-21T21:00:00Z"
    ),
    // Following morning
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Morning! Anyone up for a quick revision session before class?",
        sent_at = "2025-09-22T07:45:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "I’m awake but barely… give me coffee first ☕",
        sent_at = "2025-09-22T07:50:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I’m down. Let’s do 20 min quickfire problems?",
        sent_at = "2025-09-22T07:55:00Z"
    )
)

val messagesFive = mutableListOf(
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "Does anyone understand the last lecture?",
        sent_at = "2025-09-22T09:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Honestly, I’m still a bit lost on the wave equations part 😅",
        sent_at = "2025-09-22T09:35:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "I think I got it, but I need to review the derivation again.",
        sent_at = "2025-09-22T09:40:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_1",
        message_text = "Hi all, remember to check the lecture notes PDF. It explains the derivation step by step.",
        sent_at = "2025-09-22T10:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Thanks Dr. Brown! That helps a lot.",
        sent_at = "2025-09-22T10:05:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I’ll try to go over it tonight and see if I can solve some problems.",
        sent_at = "2025-09-22T10:15:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "lecturer_2",
        message_text = "Also, don’t forget the extra practice problems I uploaded last week.",
        sent_at = "2025-09-21T14:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Oh, I missed those! Thanks Mrs. Van Der Merwe, I’ll check them out.",
        sent_at = "2025-09-21T14:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Hey, should we meet online later to go through some examples together?",
        sent_at = "2025-09-21T18:20:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "I’m in. Maybe 7pm?",
        sent_at = "2025-09-21T18:25:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Sounds good. I’ll bring my notes and we can try a few problems.",
        sent_at = "2025-09-21T18:30:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "Perfect! See you then.",
        sent_at = "2025-09-21T18:35:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_1",
        message_text = "By the way, did anyone catch the tip about the formula on page 23? I think it helps with the derivation.",
        sent_at = "2025-09-20T21:00:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_3",
        message_text = "Yeah, that trick really clarifies things!",
        sent_at = "2025-09-20T21:10:00Z"
    ),
    InternalMessage(
        message_id = nextMessageId++,
        sender_id = "student_2",
        message_text = "I’ll try it out tomorrow morning. Fingers crossed 😂",
        sent_at = "2025-09-20T21:15:00Z"
    )
)
