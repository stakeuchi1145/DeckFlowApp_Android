package com.example.deckflowapp_android.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.deckflowapp_android.module.LoginUser
import com.example.deckflowapp_android.repository.ILoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository? = null
): ViewModel() {
    private val TAG = "LoginViewModel"
    val email = mutableStateOf("")
    val password = mutableStateOf("")

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

    suspend fun login(): LoginUser? {
        Log.d(TAG, "login: ${email.value} / ${password.value}")

        try {
            val user = loginRepository?.login(email.value, password.value)

            Log.d(TAG, "login: user=${user?.uid}")
            return LoginUser(
                id = "",
                name = "",
                email = "",
                uuid = user?.uid ?: ""
            )
        } catch (e: Exception) {
            Log.e(TAG, "login: error=${e.message}" )
        }

        return null
    }
}
