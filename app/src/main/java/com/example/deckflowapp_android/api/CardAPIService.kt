package com.example.deckflowapp_android.api

import com.example.deckflowapp_android.api.response.GetUserCardsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import javax.inject.Singleton

@Singleton
interface CardAPIService {
    @GET("me/cards")
    suspend fun getUserCards(@Header("Authorization") token: String): Response<GetUserCardsResponse>?
}
