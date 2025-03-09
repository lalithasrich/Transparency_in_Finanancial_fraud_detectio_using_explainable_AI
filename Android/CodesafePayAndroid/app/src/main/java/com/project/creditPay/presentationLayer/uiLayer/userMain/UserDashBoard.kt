package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.creditPay.R
import com.project.creditPay.dataLayer.models.DataClass
import com.project.creditPay.dataLayer.models.InitialItems
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.LIGHTGREEN
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val loader = arrayListOf(
    InitialItems(cardName = "Add Card", image = R.drawable.credit_card),
    InitialItems(cardName = "Deposit", image = R.drawable.wallet),
    InitialItems(cardName = "Transfer", image = R.drawable.transfer),
    InitialItems(cardName = "Check Fraudulent", image = R.drawable.cross),
    InitialItems(cardName = "Logout", image = R.drawable.logout)
)


@Composable
fun SomeThing(
    remember: NavHostController?,
    onFinishActivity: () -> Unit,
    shared: SharedPreferences,
    viewModel: UserViewModel,
    context: Context,
) {
    var openDialog by remember { mutableStateOf(false) }
    var dialog by remember {
        mutableStateOf(false)
    }
    if (dialog) {
        AlertDialog(onDismissRequest = {
            dialog = false
        }, confirmButton = {
            Button(onClick = {
                dialog = false
                onFinishActivity()
            }) {
                Text(text = "Yes")
            }
        }, dismissButton = {
            Button(onClick = { dialog = false }) {
                Text(text = "No")
            }
        }, text = {
            Text(text = "Do you want to Logout ?")
        }, title = {
            Text(text = "Alert!!")
        })
    }

    Row(
        modifier = Modifier.padding(5.dp, 0.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .weight(0.5f)
                .clickable {
                    remember?.navigate("AddCard")
                }, colors = CardDefaults.cardColors(MyView)
        ) {
            Image(
                painter = painterResource(id = loader[0].image),
                contentDescription =
                loader[0].cardName, modifier =
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(5.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            Text(
                text = loader[0].cardName,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
        }
        Card(
            modifier = Modifier
                .padding(5.dp)
                .weight(0.5f)
                .clickable {
                    remember?.navigate("ViewCards")
                }, colors = CardDefaults.cardColors(MyView)
        ) {
            Image(
                painter = painterResource(id = loader[1].image),
                contentDescription = loader[1].cardName, modifier =
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(5.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            Text(
                text = loader[1].cardName,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
        }

    }
    Row(
        modifier = Modifier.padding(5.dp, 0.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .weight(0.5f)
                .clickable {
                    remember?.navigate("Transfer")
                }, colors = CardDefaults.cardColors(MyView)
        ) {
            Image(
                painter = painterResource(id = loader[2].image),
                contentDescription = loader[2].cardName, modifier =
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = loader[2].cardName,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
        }
        Card(
            modifier = Modifier
                .padding(5.dp)
                .weight(0.5f)
                .clickable {
                    openDialog = true
                }, colors = CardDefaults.cardColors(MyView)
        ) {
            Image(
                painter = painterResource(id = loader[3].image),
                contentDescription = loader[3].cardName, modifier =
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(5.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            Text(
                text = loader[3].cardName,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
        }

    }
    Row(
        modifier = Modifier.padding(5.dp, 0.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .weight(0.5f)
                .clickable {
                    dialog = true
                }, colors = CardDefaults.cardColors(MyView)
        ) {
            Image(
                painter = painterResource(id = loader[4].image),
                contentDescription = loader[4].cardName, modifier =
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = loader[4].cardName,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
        }


    }
    if (openDialog) {
        CustomDialog(
            onDismissRequest = { openDialog = false },
            viewModel, context

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(onDismissRequest: () -> Unit, viewModel: UserViewModel, context: Context) {

    var income by remember { mutableStateOf("") }
    var customerAge by remember { mutableStateOf("") }
    var housingStatus by remember { mutableStateOf("") }
    var phoneMobileValid by remember { mutableStateOf("") }
    var bankMonthsCount by remember { mutableStateOf("") }
    var keepAliveSession by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            TextField(
                value = income,
                onValueChange = { income = it },
                label = { Text("Income") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            TextField(
                value = customerAge,
                onValueChange = { customerAge = it },
                label = { Text("Customer Age") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            TextField(
                value = housingStatus,
                onValueChange = { housingStatus = it },
                label = { Text("Housing Status") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            TextField(
                value = phoneMobileValid,
                onValueChange = { phoneMobileValid = it },
                label = { Text("Phone Mobile Valid") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            TextField(
                value = bankMonthsCount,
                onValueChange = { bankMonthsCount = it },
                label = { Text("Bank Months Count") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            TextField(
                value = keepAliveSession,
                onValueChange = { keepAliveSession = it },
                label = { Text("Keep Alive Session") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(onClick = onDismissRequest) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.detect(
                        income,
                        customerAge,
                        housingStatus,
                        phoneMobileValid,
                        bankMonthsCount,
                        keepAliveSession
                    ) {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Submit")
                }
            }
        }

    }
}


@SuppressLint("MutableCollectionMutableState")
@Composable
fun UserDashBoard(
    viewModel: UserViewModel,
    debitTrans: MutableStateFlow<ArrayList<DataClass>>,
    remember: NavHostController? = null,
    shared: SharedPreferences,
    context: Context,
    finishMe: () -> Unit,

    ) {

    var debit by remember {
        mutableStateOf(ArrayList<DataClass>())
    }


    debitTrans.asStateFlow().let {
        debit = it.value
    }

    var selected by remember {
        mutableStateOf("Credit")
    }
    var profile by remember {
        mutableStateOf<String?>("")
    }
    var expandable by remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf("Hi N 😊 !!")
    }

    viewModel.nameLoader.collectAsState().let {
        name = it.value ?: "1212"
        var store = ""
        name.trim().forEachIndexed { index, c ->
            store += if (index == 0) c.uppercaseChar() else c.lowercaseChar()
        }
        name = "Hi $store 😊 !!"
    }

    viewModel.pickLoader.collectAsState().let {
        profile = it.value
    }

    val rememberScrollPosition = rememberScrollState()


    LazyColumn(
        modifier = Modifier
            .background(MyView)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 50.dp, 10.dp, 10.dp)
                    .height(150.dp)
                    .verticalScroll(rememberScrollPosition)
            ) {
                Card(
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ), modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .align(Alignment.End)
                ) {
                    AsyncImage(
                        model = if (profile == null) {
                            "https://i.pinimg.com/736x/c0/74/9b/c0749b7cc401421662ae901ec8f9f660.jpg"
                        } else {
                            profile
                        }, contentDescription = null
                    )

                }
                Text(
                    text = name, fontWeight =
                    FontWeight.Medium, fontSize = TextUnit(
                        20f,
                        TextUnitType.Sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }

        }
        item {
            Column(
                modifier = Modifier
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Activities",
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(19f, TextUnitType.Sp),
                        modifier = Modifier.padding(10.dp)
                    )

                    SomeThing(remember, finishMe, shared, viewModel, context)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "Select transaction type *")

                        Button(
                            onClick = {
                                expandable = true
                            }, colors = ButtonDefaults.buttonColors(
                                MyView
                            )
                        ) {
                            Text(
                                text = selected,
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                modifier = Modifier.align(
                                    Alignment.CenterVertically
                                ),
                                color = Color.Black
                            )
                            Icon(
                                painter = painterResource(
                                    id =
                                    if (selected == "Credit") {
                                        R.drawable.up_arrow
                                    } else {
                                        R.drawable.down_arrow
                                    }
                                ),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expandable,
                            onDismissRequest = {
                                expandable = false
                            },
                            modifier = Modifier
                                .wrapContentSize(Alignment.CenterEnd)
                                .background(MyView)
                        ) {
                            items.forEach { the ->
                                DropdownMenuItem(text = {
                                    Row {
                                        Icon(
                                            painter = painterResource(id = the.image),
                                            contentDescription = the.name,
                                            tint = Color.Black
                                        )
                                        Text(text = the.name)
                                    }
                                }, onClick = {
                                    selected = the.name
                                    viewModel.finMe(the.name)
                                    expandable = false
                                })
                            }
                        }
                    }

                }
            }
        }

        items(debit) {
            var type = "Debit"
            var image = R.drawable.down_arrow
            var color = Color.Red

            if (it.to == shared.getString("id", "")) {
                color = LIGHTGREEN
                type = "Credit"
                image = R.drawable.up_arrow
            }
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 30.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(0.85f)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = color
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(25.dp, 5.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = image),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = type,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
                                    )
                                }
                            }
                            Text(
                                text = "₹${it.amount}/-", fontWeight = FontWeight.Medium,
                                color = Color.DarkGray, fontSize =
                                TextUnit(20F, TextUnitType.Sp), modifier =
                                Modifier.padding(10.dp, 0.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                            Text(text = it.time, color = Color.DarkGray)
                            VerticalDivider(
                                thickness = 5.dp, color = MyView, modifier =
                                Modifier.padding(2.dp)
                            )
                            Text(text = it.transactionID, color = Color.DarkGray)

                        }

                    }

                }
            }
        }

    }

}

val items = arrayOf(
    PointAccess(name = "Debit", image = R.drawable.down_arrow),
    PointAccess(name = "Credit", image = R.drawable.up_arrow)
)

data class PointAccess(
    var name: String,
    var image: Int,
)
