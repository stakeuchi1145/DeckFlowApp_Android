package com.example.deckflowapp_android.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.api.CardAPIService
import com.example.deckflowapp_android.api.response.CardInfo
import com.example.deckflowapp_android.repository.ICardRepository
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import com.example.deckflowapp_android.ui.compose.NetworkImage
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Text(
                text = "My Cards",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                BasicTextField(
                    value = viewModel.searchText.value,
                    onValueChange = { viewModel.onSearchTextChange(it) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF2F3F5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .weight(1f)
                        .focusable()
                        .padding(8.dp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row() {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "search",
                                tint = Color.Black,
                                modifier = Modifier.padding(end = 4.dp)
                            )

                            if (viewModel.searchText.value.isEmpty()) {
                                Text(text = "検索", color = Color.Gray)
                            } else {
                                innerTextField()
                            }
                        }
                    }
                )

                Button(
                    onClick = {
                        viewModel.fetchCards()
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Filter")
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = Color.LightGray
        )

        when (state) {
            is HomeUiState.Success -> {
                Column(
                    verticalArrangement = if ((state as HomeUiState.Success).data.isEmpty()) Arrangement.Center else Arrangement.Top,
                    horizontalAlignment = if ((state as HomeUiState.Success).data.isEmpty()) Alignment.CenterHorizontally else Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    if ((state as HomeUiState.Success).data.isNotEmpty()) {
                        (state as HomeUiState.Success).data.forEach { card ->
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(8.dp)
                                ) {
                                    NetworkImage(
                                        url = card.imageUrl,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(0.4f)
                                    )

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        Text(
                                            text = card.name,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )

                                        Text(
                                            text = card.packName,
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "x ${card.quantity}",
                                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Icon(
                                        Icons.Filled.ArrowForwardIos,
                                        contentDescription = "search",
                                        tint = Color.LightGray,
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            "カードが見つかりません。",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }
            else -> {
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .padding(20.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }

    if (state is HomeUiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Composable
fun HomeBottomBar(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /* TODO */ },
            enabled = state != HomeUiState.Loading,
        )
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

    class FakeCardRepository() : ICardRepository {
        override suspend fun getCards(token: String): List<CardInfo> {
            return listOf()
        }
    }

    val viewModel = HomeViewModel(FakeLoginRepository(), FakeCardRepository(), LoginUserService())
    val navController = rememberNavController()
    DeckFlowApp_AndroidTheme {
        Scaffold(
            bottomBar = {
                HomeBottomBar(viewModel)
            }
        ) { innerPadding ->
            HomeScreen(innerPadding, navController, viewModel)
        }
    }
}