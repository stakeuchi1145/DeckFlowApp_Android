package com.example.deckflowapp_android.ui.mycard.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deckflowapp_android.R
import com.example.deckflowapp_android.module.Card
import com.example.deckflowapp_android.ui.compose.NetworkImage
import com.example.deckflowapp_android.ui.theme.DeckFlowApp_AndroidTheme

@Composable
fun MyCardRegisterScreen(padding: PaddingValues, navController: NavController) {
    val card: Card? = Card(
        id = 0,
        name = "ピカチュウex",
        packName = "超電ブレイカー",
        imageUrl = "https://minio.deckflow.stakeuchi.work/card-images/sv8/033.jpg",
        quantity = 0
    )

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "マイカード登録",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = Color.LightGray
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.White)
            ) {
                card?.let {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        NetworkImage(
                            it.imageUrl,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxSize()
                                .weight(1f)
                                .padding(8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    it.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )

                                Text(
                                    it.packName,
                                    fontSize = 14.sp,
                                )

                                Text(
                                    "レアリティ：RRR",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF2F7FA6),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .background(Color(0xFFE6F3FB), shape = RoundedCornerShape(20.dp))
                                        .border(1.dp, Color(0xFF5FA8D3), shape = RoundedCornerShape(20.dp))
                                        .padding(all = 6.dp)
                                )

                                TextButton(
                                    onClick = {},
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                    ) {
                                        Text("別のカードを選択", fontSize = 14.sp)
                                        Icon(
                                            Icons.Default.ArrowForwardIos,
                                            "",
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(start = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } ?: run {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(20.dp))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(48.dp)
                        ) {
                            Icon(
                                Icons.Filled.CameraAlt,
                                "",
                                modifier = Modifier.size(48.dp),
                                tint = Color.Gray.copy(alpha = 0.6f)
                            )

                            Text(
                                "No Card Selected",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Button(
                        onClick = { /* TODO: Implement card selection */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text("カードを選択", fontSize = 16.sp, modifier = Modifier.padding(12.dp))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .padding(horizontal = 4.dp)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Text(
                        "Quantity",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Icon(Icons.Default.Remove, "")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(4f)
                                .padding(horizontal = 8.dp)
                                .align(Alignment.CenterVertically)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                "0",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            )
                        }

                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Icon(Icons.Default.Add, "")
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text("登録", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = PIXEL_6)
@Composable
fun MyCardRegisterScreenPreview() {
    val navController = rememberNavController()
    DeckFlowApp_AndroidTheme {
        Scaffold(
        ) { innerPadding ->
            MyCardRegisterScreen(innerPadding, navController)
        }
    }
}