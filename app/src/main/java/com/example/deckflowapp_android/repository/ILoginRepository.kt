package com.example.deckflowapp_android.repository

interface ILoginRepository {
    suspend fun getCurrentUser(): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun getToken(): String?

    suspend fun getUserInfo(token: String): String?

    suspend fun logout()
}
