package com.example.deckflowapp_android.api.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)