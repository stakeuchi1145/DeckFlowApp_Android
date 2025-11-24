package com.example.deckflowapp_android.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.R
import com.example.deckflowapp_android.repository.ILoginRepository
import com.example.deckflowapp_android.service.LoginUserService
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(innerPadding: PaddingValues, navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

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

                TextField(
                    value = viewModel.email.value,
                    onValueChange = { viewModel.onEmailChange(it) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1,
                    singleLine = true,
                    placeholder = { Text("user123@example.com") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
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

                TextField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    visualTransformation = if (viewModel.isVisiblePassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1,
                    singleLine = true,
                    placeholder = { Text("password") },
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.onVisiblePasswordChange(!viewModel.isVisiblePassword.value) },
                            content = {
                                Icon(
                                    imageVector = if (viewModel.isVisiblePassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = "Toggle visibility"
                                )
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch { scope
                            loading = true
                            if (viewModel.login()) {
                                navController.navigate("home")
                                viewModel.clearInput()
                            } else {
                                viewModel.showDialog(
                                    "ログイン失敗",
                                    "IDまたはパスワードが正しくありません。"
                                )
                            }
                            loading = false
                        }
                    },
                    enabled = viewModel.email.value.isNotEmpty() && viewModel.password.value.isNotEmpty(),
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

        if (viewModel.isDialog.value) {
            showDialog(
                viewModel.title.value,
                viewModel.message.value,
                { viewModel.clearDialog() }
            )
        }
    }

    if (loading) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showDialog(
    title: String,
    message: String,
    onDismissCallback: () -> Unit = {},
    onConfirmCallback: () -> Unit = {}
) {
    AlertDialog(
        title = { Text(title) },
        text = { Text(message) },
        onDismissRequest = {
            onDismissCallback.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissCallback.invoke()
                }
            ) {
                Text("OK")
            }
        },
    )
}

@Preview(device = PIXEL_6)
@Composable
fun LoginScreenPreview() {
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

    val viewModel = LoginViewModel(FakeLoginRepository(), LoginUserService())
    val navController = rememberNavController()

    DeckFlowApp_AndroidTheme {
        LoginScreen(PaddingValues(), navController, viewModel)
    }
}
