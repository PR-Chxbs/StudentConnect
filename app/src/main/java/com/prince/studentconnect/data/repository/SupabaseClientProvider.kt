
package com.prince.studentconnect.data.repository

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://caedsakqmkdxhjnpfzlp.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNhZWRzYWtxbWtkeGhqbnBmemxwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTc3NjM5ODYsImV4cCI6MjA3MzMzOTk4Nn0.RrrH-ztEj8qAMh5sZvDFrImJfDNCBbISwXTOyoIoZ4E"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Auth) // Authentication
        // install(Postgrest) // Database (optional)
    }

}