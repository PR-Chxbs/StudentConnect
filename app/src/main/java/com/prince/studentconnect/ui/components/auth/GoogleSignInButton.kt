package com.prince.studentconnect.ui.components.auth

import android.util.Base64
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

// Credential Manager
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest

// Google Identity Services
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.R

// Your ViewModel

// For nonce generation
import java.security.SecureRandom


@Composable
fun GoogleSignInButton(
    viewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val nonce = remember { mutableStateOf(generateNonce())}
    val clientId = stringResource(R.string.default_web_client_id)

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                viewModel.loginWithGoogleNative(googleIdToken, nonce.value)

                // Handle successful sign-in
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
            } catch (e: Exception) {
                // Handle unknown exceptions
            }
        }
    }

    Button(
        onClick = onClick ,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Row content: icon + space + text
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google logo",
            tint = Color.Unspecified, // 👈 prevents Material from recoloring the vector
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text("Continue with Google", color = Color.Black)
    }
}
private fun generateNonce(): String {
    val bytes = ByteArray(32)
    SecureRandom().nextBytes(bytes)
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}
