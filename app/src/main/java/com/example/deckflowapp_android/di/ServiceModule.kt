package com.example.deckflowapp_android.di

import com.example.deckflowapp_android.service.LoginUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun providedLoginUserService(): LoginUserService {
        return LoginUserService()
    }
}
