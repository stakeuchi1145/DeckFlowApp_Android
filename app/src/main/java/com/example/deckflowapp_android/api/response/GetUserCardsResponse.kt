package com.example.deckflowapp_android.api.response

import kotlinx.serialization.Serializable

@Serializable
data class GetUserCardsResponse(
    val myCards: List<CardInfo>
)

@Serializable
data class CardInfo(
    val id: Int,
    val cardName: String,
    val imageURL: String,
    val packName: String,
    val quantity: Int
)
