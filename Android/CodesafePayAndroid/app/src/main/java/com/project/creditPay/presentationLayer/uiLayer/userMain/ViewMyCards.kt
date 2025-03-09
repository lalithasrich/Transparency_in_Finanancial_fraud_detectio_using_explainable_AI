package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.project.creditPay.R
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.UserViewModel
import com.project.creditPay.responsiveLayer.models.CardDetails

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ViewCards(
    navController: NavController? = null,
    userViewModel: UserViewModel? = null,
) {
    var itemsView by remember {
        mutableStateOf<ArrayList<CardDetails>>(arrayListOf())
    }
    var currentBalance by remember {
        mutableStateOf(0)
    }
    var docId by remember {
        mutableStateOf("")
    }
    var dialog by remember {
        mutableStateOf(false)
    }
    var amount by remember {
        mutableStateOf("")
    }


    userViewModel?.getMyCards?.collectAsState()?.value.let {
        if (it != null) {
            itemsView = it
        }
    }
    userViewModel?._loader?.collectAsState().let {
        it?.value?.message?.let {
            if (it == "Updated") {
                dialog = false
            }
        }
    }


    Column(
        modifier = Modifier
            .background(MyView)
            .padding(10.dp, 20.dp)
            .fillMaxSize()
    ) {
        if (itemsView.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "Cards", fontWeight = FontWeight.Bold, fontSize = TextUnit(
                    15f,
                    TextUnitType.Sp
                ),
                modifier = Modifier.align(
                    Alignment.Start
                )
            )
            LazyColumn {
                items(itemsView) { array ->
                    Card(
                        colors = CardDefaults.cardColors(Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Account : ${array.balanceOf ?: 0}/-", color =
                        Color.White, modifier = Modifier
                            .align(
                                Alignment.End
                            )
                            .padding(8.dp))
                        Image(
                            painter =
                            painterResource(id = R.drawable.card),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(80.dp)
                        )
                        var store = ""
                        array.cardNumber?.forEachIndexed { index, c ->
                            store += if (index % 4 == 0) {
                                "  $c"
                            } else {
                                c
                            }
                        }
                        Text(
                            text = store,
                            fontSize = TextUnit(18f, TextUnitType.Sp),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(10.dp),
                            color = Color.White
                        )
                        Text(
                            text = "Expiry", modifier = Modifier
                                .align(
                                    Alignment.End
                                )
                                .padding(20.dp, 0.dp), fontSize =
                            TextUnit(12f, TextUnitType.Sp),
                            color = Color.White
                        )
                        Text(
                            text = "${array.expiry}", color = Color.White,
                            modifier = Modifier
                                .align(
                                    Alignment.End
                                )
                                .padding(20.dp, 5.dp)
                        )

                        Row(
                            modifier = Modifier.align(
                                Alignment.End
                            )
                        ) {
                            Text(
                                text = array.cardName.toString(),
                                fontSize = TextUnit(17f, TextUnitType.Sp),
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(20.dp, 5.dp, 10.dp, 20.dp)
                            )
                            Button(onClick = {
                                currentBalance = array.balanceOf ?: 0
                                dialog = true
                                amount="0"
                                docId = "${array.id}"
                            }) {
                                Text(text = "Deposit")
                            }
                        }

                    }
                }
            }
            if (dialog) {
                Dialog(onDismissRequest = { }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(value = amount,
                            onValueChange = { amount = it },
                            placeholder = {
                                Text(text = "Amount")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            )
                        )
                        Button(onClick = {
                            userViewModel?.updateAmount(docId, amount, currentBalance)
                        }) {
                            Text(text = "Deposit")
                        }
                    }
                }
            }

        }

    }

}