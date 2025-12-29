package com.example.deckflowapp_android.service

import com.example.deckflowapp_android.module.LoginUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUserService @Inject constructor() {
    private var loginUser: LoginUser? = null

    fun setLoginUser(name: String, email: String, password: String, token: String) {
        this.loginUser = LoginUser(name, email, password, token)
    }

    fun getDisplayName(): String? {
        return loginUser?.name
    }

    fun getToken(): String? {
        return loginUser?.token
    }

    fun clearLoginUser() {
        this.loginUser = null
    }
}