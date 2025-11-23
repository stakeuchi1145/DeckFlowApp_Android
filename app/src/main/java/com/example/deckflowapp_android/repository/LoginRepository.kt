package com.example.deckflowapp_android.repository

import android.util.Log
import com.example.deckflowapp_android.api.UserAPIService
import com.example.deckflowapp_android.api.request.LoginRequest
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class LoginRepository @Inject constructor(
    private val userApiService: UserAPIService
): ILoginRepository {
    private val TAG = "LoginRepository"
    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun login(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                userApiService.login(LoginRequest(email, password))?.let { response ->
                    Log.d(TAG, "login: API called: response=$response")
                    if (response.isSuccessful) {
                        Log.d(TAG, "login: API success")
                        response.body()?.let { loginResponse ->
                            Log.d(TAG, "login: Token ${loginResponse.token}")
                            return@withContext suspendCoroutine { continuation ->
                                auth.signInWithCustomToken(loginResponse.token)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "login: success")
                                            continuation.resume(loginResponse.token)
                                        } else {
                                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                                            continuation.resumeWithException(
                                                task.exception ?: Exception("Authentication failed")
                                            )
                                        }
                                    }
                            }
                        }
                    } else {
                        Log.e(TAG, "login: API failure ${response.errorBody()?.string()}")
                    }
                }
            } catch (error: Exception) {
                Log.e(TAG, "login: Exception $error")
                throw error
            }

            return@withContext ""
        }
}
