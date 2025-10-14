package com.prince.studentconnect.data


class AdminRepository(
    private val campusDao: CampusDao,
    private val userDao: UserDao
) {
    // CAMPUS CRUD
    suspend fun getCampuses() = campusDao.getAll()
    suspend fun createCampus(campus: Campus, currentUser: AppUser) {
        require(currentUser.role == Role.SYSTEM_ADMIN) { "Only System Admin can manage campuses" }
        campusDao.insert(campus)
    }
    suspend fun updateCampus(campus: Campus, currentUser: AppUser) {
        require(currentUser.role == Role.SYSTEM_ADMIN) { "Only System Admin can manage campuses" }
        campusDao.update(campus)
    }
    suspend fun deleteCampus(campus: Campus, currentUser: AppUser) {
        require(currentUser.role == Role.SYSTEM_ADMIN) { "Only System Admin can manage campuses" }
        campusDao.delete(campus)
    }

    // USER CRUD
    suspend fun getUsers(currentUser: AppUser): List<AppUser> {
        return when (currentUser.role) {
            Role.SYSTEM_ADMIN -> userDao.getAll()
            Role.CAMPUS_ADMIN -> userDao.getByCampus(currentUser.campusId ?: -1)
            else -> emptyList()
        }
    }
    suspend fun createUser(user: AppUser, currentUser: AppUser) {
        when (currentUser.role) {
            Role.SYSTEM_ADMIN -> userDao.insert(user)
            Role.CAMPUS_ADMIN -> {
                val allowed = setOf(Role.CAMPUS_ADMIN, Role.LECTURER, Role.STUDENT)
                require(user.campusId == currentUser.campusId) { "Wrong campus" }
                require(user.role in allowed) { "Role not allowed" }
                userDao.insert(user)
            }
            else -> throw IllegalAccessException("Not allowed")
        }
    }
    suspend fun updateUser(user: AppUser, currentUser: AppUser) {
        when (currentUser.role) {
            Role.SYSTEM_ADMIN -> userDao.update(user)
            Role.CAMPUS_ADMIN -> {
                val allowed = setOf(Role.CAMPUS_ADMIN, Role.LECTURER, Role.STUDENT)
                require(user.campusId == currentUser.campusId) { "Wrong campus" }
                require(user.role in allowed) { "Role not allowed" }
                userDao.update(user)
            }
            else -> throw IllegalAccessException("Not allowed")
        }
    }
    suspend fun deleteUser(user: AppUser, currentUser: AppUser) {
        when (currentUser.role) {
            Role.SYSTEM_ADMIN -> userDao.delete(user)
            Role.CAMPUS_ADMIN -> {
                require(user.campusId == currentUser.campusId) { "Wrong campus" }
                userDao.delete(user)
            }
            else -> throw IllegalAccessException("Not allowed")
        }
    }
}
