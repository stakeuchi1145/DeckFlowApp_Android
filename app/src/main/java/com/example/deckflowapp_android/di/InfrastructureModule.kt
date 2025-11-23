package com.example.deckflowapp_android.di

import com.example.deckflowapp_android.BuildConfig
import com.example.deckflowapp_android.api.UserAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InfrastructureModule {
    @Provides
    @Singleton
    fun providedRetrofitInstance(): Retrofit {
        // Placeholder for actual Retrofit instance provision
        return Retrofit.Builder()
            .baseUrl(BuildConfig.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providedUserApiService(retrofit: Retrofit): UserAPIService {
        return retrofit.create(UserAPIService::class.java)
    }
}