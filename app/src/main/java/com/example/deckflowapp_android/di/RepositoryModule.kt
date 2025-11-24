package com.example.deckflowapp_android.di

import com.example.deckflowapp_android.api.UserAPIService
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    private val loginRepository: ILoginRepository? = null

    @Provides
    @Singleton
    fun provideLoginRepository(userApiService: UserAPIService): ILoginRepository {
        if (loginRepository != null) {
            return loginRepository
        }

        return LoginRepository(userApiService)
    }
}