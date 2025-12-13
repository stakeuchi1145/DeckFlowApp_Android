package com.example.deckflowapp_android

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val loginUserService: LoginUserService
) : ViewModel() {
    private val TAG = "MainViewModel"

    suspend fun getUserInfo(): Boolean {
        try {
            if (!loginRepository.getCurrentUser()) return false

            val token = loginRepository.getToken() ?: ""
            if (token.isEmpty()) return false

            val displayName = loginRepository.getUserInfo(token) ?: ""
            loginUserService.setLoginUser(displayName, "", "", token)

            return displayName.isNotEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "getUserInfo: error=${e.message}" )
        }

        return false
    }
}
