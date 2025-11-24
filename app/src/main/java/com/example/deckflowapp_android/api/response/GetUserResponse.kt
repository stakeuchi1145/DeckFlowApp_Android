package com.example.deckflowapp_android.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(
    val displayName: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)
