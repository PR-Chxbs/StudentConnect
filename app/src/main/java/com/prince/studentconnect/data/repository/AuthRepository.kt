import com.prince.studentconnect.data.repository.SupabaseClientProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.auth

sealed class AuthResult {
    data class Success(val email: String?) : AuthResult()
    object RequiresEmailConfirmation : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository {

    private val client = SupabaseClientProvider.client

    suspend fun signUp(email: String, password: String): AuthResult {
        return try {
            val userInfo = client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val session = client.auth.currentSessionOrNull()

            when {
                session != null -> AuthResult.Success(session.user?.email)
                userInfo != null -> AuthResult.RequiresEmailConfirmation
                else -> AuthResult.Error("Unknown signup failure")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error during signup")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val session = client.auth.currentSessionOrNull()
            if (session != null) {
                AuthResult.Success(session.user?.email)
            } else {
                AuthResult.Error("Invalid credentials or no active session")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error during login")
        }
    }

    fun getCurrentUser() = client.auth.currentUserOrNull()

    suspend fun logout() = client.auth.signOut()
}
