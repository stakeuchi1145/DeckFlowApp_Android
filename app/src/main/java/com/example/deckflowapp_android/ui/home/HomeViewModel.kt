package com.example.deckflowapp_android.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckflowapp_android.BuildConfig
import com.example.deckflowapp_android.module.Card
import com.example.deckflowapp_android.repository.ICardRepository
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    object Loading : HomeUiState // ローディング中
    data class Success(val data: List<Card>) : HomeUiState // データ取得成功
    data class Error(val message: String) : HomeUiState // エラー発生
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: ILoginRepository,
    private val cardRepository: ICardRepository,
    private val loginUserService: LoginUserService
): ViewModel() {
    private val TAG = "HomeViewModel"

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val searchText = mutableStateOf("")
    private val cardList: MutableList<Card> = mutableListOf()

    init {
        fetchCards()
    }

    fun fetchCards() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            cardList.clear()

            try {
                val response = cardRepository.getCards(loginUserService.getToken() ?: "")
                if (response.isNotEmpty()) {
                    response.forEach { myCards ->
                        val card = Card(
                            id = myCards.id,
                            name = myCards.cardName,
                            packName = myCards.packName,
                            imageUrl = BuildConfig.imageUrl + myCards.imageURL,
                            quantity = myCards.quantity
                        )

                        cardList.add(card)
                    }
                }
                _uiState.value = HomeUiState.Success(data = cardList)
            } catch (error: Exception) {
                _uiState.value = HomeUiState.Error(message = "Failed to load cards: ${error.message}")
            }
        }
    }

    fun onSearchTextChange(newText: String) {
        searchText.value = newText
    }

    fun searchCards(): List<Card> {
        return cardList.filter { card ->
            card.name.contains(searchText.value, ignoreCase = true)
        }
    }

    fun getUserName(): String {
        return loginUserService.getDisplayName() ?: "Guest"
    }

    suspend fun logout() {
        userRepository.logout()
        loginUserService.clearLoginUser()
    }
}