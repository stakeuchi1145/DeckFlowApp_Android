package com.example.deckflowapp_android.ui.login

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.deckflowapp_android.R
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(innerPadding: PaddingValues, viewModel: LoginViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
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
                            Log.d("LoginScreen", "userId: ${viewModel.email}, password: ${viewModel.password}")
                            if (viewModel.login()) {
                                showToast(context, "ログインに成功しました")
                            } else {
                                showToast(context, "ログインに失敗しました")
                            }
                        }
                    },
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

fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(device = PIXEL_6)
@Composable
fun LoginScreenPreview() {
    DeckFlowApp_AndroidTheme {
        LoginScreen(PaddingValues())
    }
}
