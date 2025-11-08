package com.prince.studentconnect.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.github.jan.supabase.gotrue.auth
import com.prince.studentconnect.data.repository.SupabaseClientProvider
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken

sealed class AuthResult {
    data class Success(val email: String?) : AuthResult()
    object RequiresEmailConfirmation : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository {

    private val client = SupabaseClientProvider.client

    // Email/password signup
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

    // Email/password login
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

    // Google login (browser-based)
    fun loginWithGoogle(context: Context): AuthResult {
        return try {
            val url = SupabaseClientProvider.getGoogleOAuthUrl()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
            AuthResult.Success(null)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error during Google login")
        }
    }

    suspend fun loginWithGoogleNative(idToken: String, nonce: String): AuthResult {
        return try {
            client.auth.signInWith(IDToken) {
                this.idToken = idToken
                this.provider = Google
                this.nonce = nonce
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
