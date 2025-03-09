package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.project.creditPay.R
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.UserViewModel
import com.project.creditPay.responsiveLayer.models.Users


@SuppressLint("MutableCollectionMutableState")
@Composable
fun TransferMoneyForUser(remember: NavHostController, activity: UserViewModel) {
    var user by remember {
        mutableStateOf((ArrayList<Users>()))
    }

    activity.users.collectAsState().let {
        user = it.value
    }
    activity.users
    var values by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(MyView)
            .fillMaxSize()
    ) {
        Text(text = "Search users", fontSize = TextUnit(15f, TextUnitType.Sp)
            ,fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp,20.dp, 5.dp, 5.dp))
        TextField(
            value = values, onValueChange = {
                    password = it.isNotEmpty()
                activity.searchMe(it)
                values = it
            }, shape = RoundedCornerShape(10.dp), colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.user), contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (password) {
                        values = ""
                    }
                }) {
                    if (password) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = null
                        )
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 20.dp, 5.dp, 5.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp, 5.dp)
                .fillMaxWidth()
        ) {
            if (user.isEmpty()) {
                item {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
            items(user) { users ->
                Card(
                    colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            val jsonString = Uri.encode(Gson().toJson(users))
                            remember.navigate("sendMoney/$jsonString")
                        },
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = users.name.toString(), fontSize = TextUnit(20f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "${users.mobile}")

                    }
                }
            }
        }
    }

}