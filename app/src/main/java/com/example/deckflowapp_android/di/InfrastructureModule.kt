package com.example.deckflowapp_android.di

import com.example.deckflowapp_android.BuildConfig
import com.example.deckflowapp_android.api.UserAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InfrastructureModule {
    @Provides
    @Singleton
    fun providedOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()

                var response = chain.proceed(request)
                return@Interceptor response
            })
            .readTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun providedRetrofitInstance(client: OkHttpClient): Retrofit {
        // Placeholder for actual Retrofit instance provision
        return Retrofit.Builder()
            .baseUrl(BuildConfig.url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providedUserApiService(retrofit: Retrofit): UserAPIService {
        return retrofit.create(UserAPIService::class.java)
    }
}