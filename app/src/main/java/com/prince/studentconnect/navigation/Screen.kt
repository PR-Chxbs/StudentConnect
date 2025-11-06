package com.prince.studentconnect.navigation

sealed class Screen(val route: String) {
    // --------- Auth screens ---------
    object Login : Screen("login")
    object Register : Screen("register")
    object OnboardingPersonalDetails : Screen("onboarding_personal_details")
    object OnboardingCampus : Screen("onboarding_campus")
    object OnboardingCourse : Screen("onboarding_course")

    // --------- System Admin screens ---------
    // Main Nav
    object SystemAdminHome : Screen("system_admin_home")
    object SystemAdminManageUsers : Screen("system_admin_manage_users")
    object SystemAdminManageCampuses : Screen("system_admin_manage_campuses")
    object SystemAdminManageInterests : Screen("system_admin_manage_interests")
    object SystemAdminViewProfile : Screen("system_admin_view_profile/{user_id}")

    // Extras
    object CampusDetails : Screen("campus_details/{campus_id}")
    object EditCampus : Screen("edit_campus/{campus_id}")
    object CreateUser : Screen("create_user")

    // --------- Campus Admin screens ---------
    // Main Nav
    object CampusAdminHome : Screen("campus_admin_home")
    object CampusAdminManageUsers : Screen("campus_admin_manage_users")
    object CampusAdminManageCourses : Screen("campus_admin_manage_courses")
    object CampusAdminManageModules : Screen("campus_admin_manage_modules")
    object CampusAdminViewProfile : Screen("campus_admin_profile/{user_id}")

    // Extras
    object EditModule : Screen("edit_module/{module_id}")

    // --------- Student screens ---------
    // Main Nav
    object StudentHome : Screen("student_home")
    object StudentSearch : Screen("student_search")
    object StudentMessages : Screen("student_messages")
    object StudentCalendar : Screen("student_calendar")
    object StudentProfile : Screen("student_profile/{user_id}")

    // Chat Extra
    object StudentConversationMessages : Screen("student_conversation_messages/{conversation_id}")

    // Calendar Extra
    object StudentEventDetails : Screen("student_event_details/{event_id}")
    object StudentAddEvent : Screen("student_add_event")

    object StudentSettings: Screen("student_settings")

    // --------- Lecturer screens ---------
    // Main Nav
    object LecturerHome : Screen("lecturer_home")
    object LecturerSearch : Screen("lecturer_search")
    object LecturerMessages : Screen("lecturer_messages")
    object LecturerCalendar : Screen("lecturer_calendar")
    object LecturerProfile : Screen("lecturer_profile")

    // Navs
    object Auth : Screen("auth")   // For NavHost startDestination
    object Student : Screen("student")   // Root of student graph
    object Lecturer : Screen("lecturer")   // Root of lecturer graph
    object SystemAdmin : Screen("system_admin") // Root of system admin graph
    object CampusAdmin : Screen("campus_admin") // Root of campus admin graph

}
