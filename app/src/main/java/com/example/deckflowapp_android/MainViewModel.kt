package com.example.deckflowapp_android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckflowapp_android.module.Card
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import com.example.deckflowapp_android.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MainUiState {
    object Loading : MainUiState // ローディング中
    data class Success(val message: String) : MainUiState // データ取得成功
    data class Error(val message: String) : MainUiState // エラー発生
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val loginUserService: LoginUserService
) : ViewModel() {
    private val TAG = "MainViewModel"

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (getUserInfo()) {
                _uiState.value = MainUiState.Success("User info retrieved successfully")
            } else {
                _uiState.value = MainUiState.Error("Failed to retrieve user info")
            }
        }
    }

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
