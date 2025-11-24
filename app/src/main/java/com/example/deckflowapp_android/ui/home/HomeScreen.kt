package com.example.deckflowapp_android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome, ${viewModel.getUserName()}!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.logout()
                            navController.navigate("login")
                        }
                    },
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Text(text = "ログアウト")
                }
            }
        }
    }
}

@Preview(device = PIXEL_6)
@Composable
fun HomeScreenPreview() {
    class FakeLoginRepository : ILoginRepository {
        override suspend fun login(email: String, password: String): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun getToken(): String? {
            TODO("Not yet implemented")
        }

        override suspend fun getUserInfo(token: String): String? {
            TODO("Not yet implemented")
        }

        override suspend fun logout() {
            TODO("Not yet implemented")
        }
    }

    val viewModel = HomeViewModel(FakeLoginRepository(), LoginUserService())
    val navController = rememberNavController()
    DeckFlowApp_AndroidTheme {
        HomeScreen(PaddingValues(), navController, viewModel)
    }
}