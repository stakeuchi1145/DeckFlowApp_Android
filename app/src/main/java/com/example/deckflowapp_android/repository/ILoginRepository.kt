package com.example.deckflowapp_android.repository

import com.google.firebase.auth.FirebaseUser

interface ILoginRepository {
    suspend fun login(email: String, password: String): String?
}
