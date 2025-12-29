package com.example.deckflowapp_android

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import com.example.deckflowapp_android.ui.home.HomeScreen
import com.example.deckflowapp_android.ui.login.LoginScreen
import com.example.deckflowapp_android.ui.mycard.register.MyCardRegisterScreen
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeckFlowApp_AndroidTheme {
                DisplayNav()
            }
        }
    }
}

@Composable
fun DisplayNav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            when (currentRoute) {
                else -> {}
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "launch") {
            composable("launch") { LaunchAppScreen(innerPadding, navController) }
            composable("login") { LoginScreen(innerPadding, navController) }
            composable("home") { HomeScreen(innerPadding, navController) }
            composable("register_my_card") { MyCardRegisterScreen(innerPadding, navController) }
        }
    }
}

@Composable
fun LaunchAppScreen(innerPadding: PaddingValues, navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    CoroutineScope(Dispatchers.Main).launch {
        if (viewModel.getUserInfo()) {
            navController.navigate("home") {
                popUpTo("launch") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("launch") { inclusive = true }
            }
        }
    }

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
                    .size(250.dp)
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

@Preview(device = PIXEL_6)
@Composable
fun LaunchAppScreenPreview() {
    class FakeLoginRepository : ILoginRepository {
        override suspend fun getCurrentUser(): Boolean {
            TODO("Not yet implemented")
        }

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

    val viewModel = MainViewModel(FakeLoginRepository(), LoginUserService())
    val navController = rememberNavController()

    DeckFlowApp_AndroidTheme {
        LaunchAppScreen(PaddingValues(), navController, viewModel)
    }}

