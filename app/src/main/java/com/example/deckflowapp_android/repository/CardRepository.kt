package com.example.deckflowapp_android.repository

import android.util.Log
import com.example.deckflowapp_android.api.CardAPIService
import com.example.deckflowapp_android.api.response.CardInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(
    private val apiService: CardAPIService
) : ICardRepository {
    override suspend fun getCards(token: String): List<CardInfo> = withContext(Dispatchers.IO) {
        val response = apiService.getUserCards("Bearer $token") ?: throw IllegalStateException("API response is null")
        Log.d("CardRepository", "API Response: $response")

        if (response.isSuccessful) {
            return@withContext response.body()?.myCards ?: throw IllegalStateException("Response body is null")
        } else {
            throw IllegalStateException("API call failed with code: ${response.code()}")
        }
    }
}