package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.creditPay.R
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCard(nav: NavController? = null, user: UserViewModel? = null) {

    var cardDetails by remember {
        mutableStateOf("")
    }
    var holderName by remember {
        mutableStateOf("")
    }
    var expiry by remember {
        mutableStateOf("")
    }
    var cvv by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            IconButton(onClick = {
                nav?.navigateUp()
            }) {
                Icon(
                    painter = painterResource(
                        id =
                        R.drawable.back_btn
                    ), contentDescription = null
                )
            }
        }, modifier = Modifier
            .padding(0.dp, 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp, 50.dp)
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.credit_card),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )

            Text(
                text = "Start typing to add your credit card details.\nEverything will update according to your date.",
                modifier = Modifier.padding(2.5.dp, 10.dp)
            )

            Card(
                border = BorderStroke(1.5.dp, Color.Gray),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier.padding(0.dp, 10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.5.dp, Color.Gray), modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 15.dp),
                    color = MyView
                ) {
                    TextField(
                        value = cardDetails,
                        onValueChange = {
                            cardDetails = it
                        },
                        placeholder = {
                            Text(text = "Card number")
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        visualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions.Default,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone
                        )
                    )

                }
                Surface(
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.5.dp, Color.Gray), modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 5.dp),
                    color = MyView
                ) {
                    TextField(
                        value = holderName,
                        onValueChange = {
                            holderName = it
                        },
                        placeholder = {
                            Text(text = "Card holder name")
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        visualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions.Default,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        )
                    )

                }
                Row {
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(1.5.dp, Color.Gray), modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 5.dp)
                            .weight(0.7f),
                        color = MyView
                    ) {
                        TextField(
                            value = expiry,
                            onValueChange = {
                                expiry = it
                            },
                            placeholder = {
                                Text(text = "Expiry Monayth/Year")
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = TextFieldDefaults.colors(
                                disabledIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            visualTransformation = VisualTransformation.None,
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Phone
                            )
                        )

                    }
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(1.5.dp, Color.Gray), modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 5.dp)
                            .weight(0.3f),
                        color = MyView
                    ) {
                        TextField(
                            value = cvv,
                            onValueChange = {
                                cvv = it
                            },
                            placeholder = {
                                Text(text = "CVV")
                            },
                            shape = RoundedCornerShape(5.dp),
                            colors = TextFieldDefaults.colors(
                                disabledIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.NumberPassword
                            )
                        )

                    }

                }
                Button(
                    onClick = {
                        user?.let {
                            if (cardDetails.isEmpty()) {
                                it._loader.value= LoadersResults("Please enter your Card number")
                            }else if(cardDetails.length!=17){
                                it._loader.value= LoadersResults("Please enter a valid card number")
                            }else if(holderName.isEmpty()){
                                it._loader.value=LoadersResults("Please enter Holder name")
                            }else if(expiry.isEmpty()){
                                it._loader.value=LoadersResults("Please enter Expiry")
                            }else if(cvv.isEmpty()){
                                it._loader.value=LoadersResults("Please enter CVV")
                            }else{
                                it.addCard(
                                    cardName = holderName,
                                    cardNumber = cardDetails,
                                    expiry = expiry,
                                    cvv = cvv,
                                    sessionTime = "${System.currentTimeMillis()}"
                                )
                            }
                        }
                    }, modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(20.dp, 25.dp)
                ) {
                    Text(text = "Add", modifier = Modifier.padding(20.dp, 0.dp))
                }
            }

        }
    }

}