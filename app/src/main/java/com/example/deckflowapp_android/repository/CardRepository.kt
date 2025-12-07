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
        try {
            Log.d("", "getCards()")
            apiService.getUserCards("Bearer $token")?.let { response ->
                Log.d("CardRepository", "API Response: $response")
                if (response.isSuccessful) {
                    response.body()?.let { cards ->
                        return@withContext cards.myCards
                    }
                }
            }
        } catch (error: Exception) {
            throw error
        }

        return@withContext listOf()
    }
}