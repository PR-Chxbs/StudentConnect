package com.prince.studentconnect.data.local

import androidx.room.*
import com.prince.studentconnect.data.local.Dao.*
import com.prince.studentconnect.data.local.Entities.*
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [
        User::class,
        Campus::class,
        Module::class,
        Interest::class,
        User_interest::class,
        Conversation::class,
        Conversation_Member::class,
        Course::class,
        Course_Module::class,
        Event::class,
        Event_participation::class,
        Message::class,
        Module_Teacher::class
    ],
    version = 1,
    exportSchema = false
)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun campusDao(): CampusDao
    abstract fun moduleDao(): ModuleDao
    abstract fun interestDao(): InterestDao
    abstract fun userInterestDao(): UserInterestDao
    abstract fun conversationDao(): ConversationDao
    abstract fun moduleTeacherDao(): ModuleTeacherDao
    abstract fun messageDao(): MessageDao
    abstract fun eventParticipationDao(): EventParticipationDao
    abstract fun eventDao(): EventDao
    abstract fun courseModuleDao(): CourseModuleDao
    abstract fun courseDao(): CourseDao
    abstract fun conversationMemberDao(): ConversationMemberDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: android.content.Context): LocalDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(context.applicationContext,LocalDatabase::class.java, "student_connect_db").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}