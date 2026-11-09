package com.example.panitiahub.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseConfig {
    const val SUPABASE_URL = "https://jxfyrciiokzehqzvywal.supabase.co"
    const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imp4ZnlyY2lpb2t6ZWhxenZ5d2FsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTQyMTQyMDIsImV4cCI6MjEwOTc5MDIwMn0.AIk-G_gtBz8qUSdbxOmx59wdpXZaHxJ8nkRxTjJVky0"
}

val supabaseClient: SupabaseClient by lazy {
    createSupabaseClient(
        supabaseUrl = SupabaseConfig.SUPABASE_URL,
        supabaseKey = SupabaseConfig.SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}
