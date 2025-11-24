package com.example.deckflowapp_android.api

import android.telecom.Call
import com.example.deckflowapp_android.api.request.LoginRequest
import com.example.deckflowapp_android.api.response.GetUserResponse
import com.example.deckflowapp_android.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserAPIService {
    @POST("login")
    suspend fun login(@Body query: LoginRequest): Response<LoginResponse>?

    @GET("me")
    suspend fun getUser(@Header("Authorization") token: String): Response<GetUserResponse>?
}
