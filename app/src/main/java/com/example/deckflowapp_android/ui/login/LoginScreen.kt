package com.example.deckflowapp_android.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deckflowapp_android.R
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme

@Composable
fun LoginScreen(innerPadding: PaddingValues, viewModel: LoginViewModel) {
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
            Image(
                painter = painterResource(R.drawable.logo_title),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(250.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    "ID",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                var userId by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = userId,
                    onValueChange = { userId = it },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    "Password",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                var password by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(140.dp)
                        .height(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(8.dp)
                            )
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("ログイン")
                }
            }
        }
    }
}

@Preview(device = PIXEL_6)
@Composable
fun LoginScreenPreview() {
    val viewModel = LoginViewModel()
    DeckFlowApp_AndroidTheme {
        LoginScreen(PaddingValues(), viewModel)
    }
}
