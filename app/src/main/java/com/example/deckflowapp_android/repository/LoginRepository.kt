package com.example.deckflowapp_android.repository

import android.util.Log
import com.example.deckflowapp_android.api.UserAPIService
import com.example.deckflowapp_android.api.request.LoginRequest
import com.example.deckflowapp_android.module.LoginUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    override suspend fun getCurrentUser(): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine<Boolean> { continuation ->
                val currentUser = auth.currentUser
                continuation.resume(
                    if (currentUser != null) {
                        true
                    } else {
                        false
                    }
                )
            }
        }

    override suspend fun login(email: String, password: String) =
        withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine { continuation ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(true)
                        } else {
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                            continuation.resumeWithException(
                                task.exception ?: Exception("Authentication failed")
                            )
                        }
                    }
            }
        }

    override suspend fun getToken(): String? =
        withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine { continuation ->
                auth.currentUser?.let { user ->
                    user.getIdToken(true).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(task.result?.token ?: "")
                        } else {
                            Log.e(TAG, "getToken:failure", task.exception)
                            continuation.resumeWithException(
                                task.exception ?: Exception("Authentication failed")
                            )
                        }
                    }
                } ?: continuation.resumeWithException(Exception("Authentication failed"))
            }
        }

    override suspend fun getUserInfo(token: String): String? =
        withContext(Dispatchers.IO) {
            try {
                userApiService.getUser(token = "Bearer $token")?.let { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { userResponse ->
                            return@withContext userResponse.displayName
                        }
                    }
                }
            } catch (error: Exception) {
                Log.e(TAG, "getCurrentUser: Exception $error")
                throw error
            }

            return@withContext null
        }

    override suspend fun logout() =
        withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine { continuation ->
                Log.d(TAG, "current user before signOut: ${auth.currentUser}")
                auth.signOut()
                Log.d(TAG, "current user after signOut: ${auth.currentUser}")
                continuation.resume(Unit)
            }
        }
}
