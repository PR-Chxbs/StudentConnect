package com.prince.studentconnect.data.fakerepository

import kotlinx.coroutines.delay

sealed class AuthResult {
    data class Success(val email: String?) : AuthResult()
    object RequiresEmailConfirmation : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class FakeAuthRepository {

    private var currentUser: String? = null

    suspend fun signUp(email: String, password: String): AuthResult {
        delay(1000) // simulate network latency
        return if (email.contains("@")) {
            currentUser = email
            AuthResult.Success(email)
        } else {
            AuthResult.Error("Invalid email format")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        delay(1000) // simulate network latency
        return if (currentUser == email && password == "password") {
            AuthResult.Success(email)
        } else {
            AuthResult.Error("Invalid credentials")
        }
    }

    fun getCurrentUser(): String? = currentUser

    suspend fun logout() {
        delay(500) // simulate network latency
        currentUser = null
    }
}
