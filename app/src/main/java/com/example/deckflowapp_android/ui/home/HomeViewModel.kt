package com.example.deckflowapp_android.ui.home

import androidx.lifecycle.ViewModel
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: ILoginRepository,
    private val loginUserService: LoginUserService
): ViewModel() {
    fun getUserName(): String {
        return loginUserService.getDisplayName() ?: "Guest"
    }

    suspend fun logout() {
        userRepository.logout()
        loginUserService.clearLoginUser()
    }
}