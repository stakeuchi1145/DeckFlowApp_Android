package com.example.deckflowapp_android.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val loginService: LoginUserService
): ViewModel() {
    private val TAG = "LoginViewModel"
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val displayName = mutableStateOf("")
    val token = mutableStateOf("")
    val isDialog = mutableStateOf(false)
    val title = mutableStateOf("")
    val message = mutableStateOf("")

    val isVisiblePassword = mutableStateOf(true)

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onVisiblePasswordChange(isVisible: Boolean) {
        isVisiblePassword.value = isVisible
    }

    fun clearInput() {
        email.value = ""
        password.value = ""
    }

    fun showDialog(title: String, message: String) {
        this.title.value = title
        this.message.value = message
        isDialog.value = true
    }

    fun clearDialog() {
        isDialog.value = false
        title.value = ""
        message.value = ""
    }

    suspend fun login(): Boolean {
        try {
            if (loginRepository.login(email.value, password.value)) {
                this.token.value =  loginRepository.getToken() ?: ""
                if (!this.token.value.isNotEmpty()) return false
            } else {
                return false
            }

            return getUserInfo()
        } catch (e: Exception) {
            Log.e(TAG, "login: error=${e.message}" )
        }

        return false
    }

    suspend fun getUserInfo(): Boolean {
        try {
            displayName.value = loginRepository.getUserInfo(this.token.value) ?: ""
            loginService.setLoginUser(displayName.value, email.value, password.value, this.token.value)
            return displayName.value.isNotEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "getUserInfo: error=${e.message}" )
        }

        return false
    }
}
