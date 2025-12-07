package com.example.deckflowapp_android.repository

import com.example.deckflowapp_android.api.CardAPIService
import com.example.deckflowapp_android.api.response.CardInfo

interface ICardRepository {
    suspend fun getCards(token: String): List<CardInfo>
}