package com.example.deckflowapp_android.ui.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.MainUiState
import com.example.deckflowapp_android.MainViewModel
import com.example.deckflowapp_android.R
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme

@Composable
fun LaunchAppScreen(innerPadding: PaddingValues, navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state) {
        when(state) {
            is MainUiState.Success -> {
                navController.navigate("home") {
                    popUpTo("launch") { inclusive = true }
                }
            }
            is MainUiState.Error -> {
                navController.navigate("login") {
                    popUpTo("launch") { inclusive = true }
                }
            }
            else -> {}
        }
    }

    when (state) {
        is MainUiState.Loading -> {
            // ローディング中のUIは下部で表示
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo_title),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    CircularProgressIndicator(
                        modifier = Modifier.size(120.dp).padding(10.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )

                    Text("Loading...")
                }
            }
        }
        else -> {}
    }
}

@Preview(device = PIXEL_6)
@Composable
fun LaunchAppScreenPreview() {
    val viewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()

    DeckFlowApp_AndroidTheme {
        LaunchAppScreen(PaddingValues(), navController, viewModel)
    }
}
