package com.prince.studentconnect.navigation

sealed class Screen(val route: String) {
    // --------- Auth screens ---------
    object Login : Screen("login")
    object Register : Screen("register")
    object PostRegister: Screen("post_register")

    // --------- System Admin screens ---------
    // Main Nav
    object SystemAdminHome : Screen("system_admin_home")
    object SystemAdminManageUsers : Screen("system_admin_manage_users")
    object SystemAdminManageCampuses : Screen("system_admin_manage_campuses")
    object SystemAdminManageInterests : Screen("system_admin_manage_interests")
    object SystemAdminProfile : Screen("system_admin_profile")

    // --------- Campus Admin screens ---------
    // Main Nav
    object CampusAdminHome : Screen("campus_admin_home")
    object CampusAdminManageUsers : Screen("campus_admin_manage_users")
    object CampusAdminManageCourses : Screen("campus_admin_manage_courses")
    object CampusAdminManageModules : Screen("campus_admin_manage_modules")
    object CampusAdminProfile : Screen("campus_admin_profile")

    // --------- Student screens ---------
    // Main Nav
    object StudentHome : Screen("student_home")
    object StudentSearch : Screen("student_search")
    object StudentMessages : Screen("student_messages")
    object StudentCalendar : Screen("student_calendar")
    object StudentProfile : Screen("student_profile")

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
