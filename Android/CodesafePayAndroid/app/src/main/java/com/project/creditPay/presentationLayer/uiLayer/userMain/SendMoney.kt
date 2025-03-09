package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import com.project.creditPay.R
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.viewModels.UserViewModel
import com.project.creditPay.responsiveLayer.models.CardDetails
import com.project.creditPay.responsiveLayer.models.Users
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoney(
    remember: NavHostController,
    activity: UserViewModel,
    users: Users,
) {

    var stateOf by remember {
        activity.statePoint.value = false
        mutableStateOf(activity.statePoint.value)
    }
    var pinEntering by remember {
        activity.stateOfTransGetter.value = false
        mutableStateOf(activity.stateOfTransGetter.value)
    }
    var successText by remember {
        activity.successTextPoint.value = ""
        mutableStateOf(activity.successTextPoint.value)
    }
    activity.successTextPoint.collectAsState().let {
        successText = it.value
    }

    var errorState by remember {
        mutableStateOf(false)
    }
    var amount by remember {
        mutableStateOf("")
    }

    activity.stateOfTransGetter.collectAsState().let {
        pinEntering = it.value
    }
    var mutableCard by remember {
        mutableStateOf<CardDetails?>(null)
    }
    var cards by remember {
        mutableStateOf<ArrayList<CardDetails>>(arrayListOf())
    }
    var pinText by remember {
        mutableStateOf("")
    }
    activity.getMyCards.collectAsState().value.let {
        cards = it
    }
    activity.statePoint.collectAsState().value.let {
        stateOf = it
    }


    val sheetState = rememberModalBottomSheetState()

    var stateOfDialog by remember {
        mutableStateOf(false)
    }
    val coroutine = rememberCoroutineScope()
    if (stateOfDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutine.launch { sheetState.hide() }
                stateOfDialog = false
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (cards.isEmpty()) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn {
                        items(cards) { cardPoint ->
                            Column(
                                modifier = Modifier.clickable {
                                    stateOfDialog = false
                                    val k = amount.toIntOrNull()
                                    if (k == null) {
                                        activity._loader.value = LoadersResults(
                                            message = "Invalid Amount"
                                        )
                                    }
                                    k?.let { the ->
                                        cardPoint.balanceOf?.let {
                                            if (the <= it) {
                                                mutableCard = cardPoint
                                            } else {
                                                activity._loader.value = LoadersResults(
                                                    message = "Insufficient Balance"
                                                )
                                            }
                                        }
                                    }
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = cardPoint.cardNumber.toString(),
                                        fontSize = TextUnit(20f, TextUnitType.Sp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = "${cardPoint.expiry}")
                                }
                                HorizontalDivider(thickness = 0.8.dp, color = Color.LightGray)
                            }
                        }
                    }
                }

            }
        }
    }
    activity._loader.collectAsState().let {
        it.value.message?.let {
            if (it == "Transaction Success") {
                remember.navigate("Home") {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
                pinEntering = false
            }
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        mutableCard?.let {
            activity.statePoint.value = true
            Text(
                text = "From",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(16f, TextUnitType.Sp),
            )
            CardShow(it, activity)
            Text(
                text = "To",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(16f, TextUnitType.Sp),
            )
            ToCard(users)
        }
        Surface(
            shape = RoundedCornerShape(10.dp)
        ) {
            if (stateOf) {
                Text(
                    text = "Transfer : ₹ $amount/-",
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(
                        20f,
                        TextUnitType.Sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            } else {
                TextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ), placeholder = { Text(text = "Amount") }
                )
            }

        }
        if (!stateOf) {
            Button(onClick = {
                stateOfDialog = true
            }) {
                Text(text = "Submit")
            }
        }
    }
    if (pinEntering) {
        Dialog(onDismissRequest = { pinEntering = false }) {
            if (mutableCard != null) {
                errorState = mutableCard!!.cvv != pinText
            }
            Card(
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    var text = "Authentication Sent"
                    if (successText == "") {
                        text = "Authentication Pending"
                    }
                    Text(
                        text = text,
                        fontSize = TextUnit(15f, TextUnitType.Sp),
                        modifier = Modifier.padding(10.dp)
                    )
                    if (successText != "") {
                        Text(
                            text = successText, color = Color.Blue, fontSize = TextUnit(
                                20f,
                                TextUnitType.Sp
                            ), modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            )
                        )


                    }

                    TextField(
                        value = pinText,
                        onValueChange = {
                            pinText = it
                        }, isError = errorState, supportingText = {
                            if (errorState) {
                                Text(text = "Please enter a valid CVV")
                            } else {
                                Text(text = "Correct CVV", color = Color.Green)
                            }
                        }, leadingIcon = {
                            if (successText == "") {
                                Icon(
                                    painter = painterResource(id = R.drawable.coin),
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.password_with_dots),
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (successText == "") {
                                KeyboardType.NumberPassword
                            } else {
                                KeyboardType.Text
                            }
                        ), placeholder = {
                            if (successText == "") {
                                Text(text = "CVV")
                            } else {
                                Text(text = "Enter your Authentication ID")
                            }

                        }
                    )
                    Button(
                        onClick = {
                            if (pinText.isNotEmpty()) {
                                if (successText == "") {
                                    activity.successTextPoint.value = start + middle + symbol
                                    pinText = ""
                                } else {
                                    if (successText != pinText) {
                                        activity._loader.value =
                                            LoadersResults("Invalid Authentication Number")
                                    } else {
                                        activity._loader.value = LoadersResults(condition = true)
                                        activity.transferMoney(
                                            mutableCard,
                                            users,
                                            amount.toIntOrNull()
                                        )
                                        pinEntering = false
                                        errorState = false
                                    }
                                }
                            }


                        }, modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    ) {
                        Text(text = "Submit")
                    }
                }

            }
        }
    }
}

const val text = "ABCDEFGHIJKLMNOPQRSTVUWXYZ"
const val text2 = "abcdefghijklmnopqrstvuwxyz"
const val symbols = "!@#$%^&*()><?/"


val start = text[(0..text.length).random()].toString() + text[(0..text.length).random()]
val middle = text2[(0..text2.length).random()].toString() + text2[(0..text2.length).random()]
val symbol =
    symbols[(0..symbols.length).random()].toString() + symbols[(0..symbols.length).random()]


@Composable
fun ToCard(users: Users) {
    Card(
        colors = CardDefaults.cardColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                text = "${users.name}",
                fontSize = TextUnit(18f, TextUnitType.Sp),
                color = Color.White
            )
            Text(
                text = "${users.mobile}",
                fontSize = TextUnit(16f, TextUnitType.Sp),
                color = Color.White
            )
        }

    }
}

@Composable
fun CardShow(mutableCard: CardDetails, activity: UserViewModel) {
    Card(
        colors = CardDefaults.cardColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Account : ${mutableCard.balanceOf ?: 0}/-", color =
            Color.White, modifier = Modifier
                .align(
                    Alignment.End
                )
                .padding(8.dp)
        )
        Image(
            painter =
            painterResource(id = R.drawable.card),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
        )
        var store = ""
        mutableCard.cardNumber?.forEachIndexed { index, c ->
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
            text = "${mutableCard.expiry}", color = Color.White,
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
                text = mutableCard.cardName.toString(),
                fontSize = TextUnit(17f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier
                    .padding(20.dp, 5.dp, 10.dp, 20.dp)
                    .align(
                        Alignment.CenterVertically
                    )
            )
            Button(onClick = {
                activity.stateOfTransGetter.value = true
            }, modifier = Modifier.padding(5.dp)) {

                Text(text = "Send")
            }
        }

    }
}
