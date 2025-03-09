package com.project.creditPay.presentationLayer.uiLayer.userMain

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.SplashActivity
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.CodeSafePayAndroidTheme
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.UserViewModel
import com.project.creditPay.responsiveLayer.models.Users
import kotlinx.coroutines.launch

class UserMain : ComponentActivity() {
    private val activity: UserViewModel by viewModels {
        UserViewModel.getSharedViewModel(getSharedPreferences("user", MODE_PRIVATE))
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activity.transaction()
        setContent {
            CodeSafePayAndroidTheme {
                val remember = rememberNavController()
                var snackMessage by remember {
                    mutableStateOf<String?>(null)
                }
                var loaderBoolean by remember {
                    mutableStateOf<Boolean?>(null)
                }
                activity._loader.collectAsState().let {
                    it.let { it1 ->
                        loaderBoolean = it1.value.condition
                        snackMessage = it1.value.message

                        if (it1.value.message != null || it1.value.condition != null) {
                            activity._loader.value = LoadersResults()
                        }
                    }
                }

                val snackBar = remember { SnackbarHostState() }
                val coroutine = rememberCoroutineScope()
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBar)
                    }, modifier = Modifier
                        .fillMaxSize()
                        .background(MyView)
                ) { _ ->
                    NavHost(navController = remember, startDestination = "Home") {

                        composable("Home") {
                            UserDashBoard(
                                activity, activity.transactions,
                                remember, getSharedPreferences(
                                    "user",
                                    MODE_PRIVATE
                                ),applicationContext
                            ) {
                                getSharedPreferences("user",
                                    MODE_PRIVATE).edit().clear().apply()
                                finishAffinity()
                                startActivity(
                                    Intent(
                                        applicationContext, SplashActivity::class.java
                                    )
                                )


                            }
                        }
                        composable("AddCard") {
                            AddCard(remember, activity)
                        }
                        composable("ViewCards") {
                            ViewCards(navController = remember, userViewModel = activity)
                        }
                        composable("Transfer") {
                            TransferMoneyForUser(remember, activity)
                        }
                        composable("sendMoney/{data}") {
                            val data = it.arguments?.getString("data")
                            val dataPoint = Gson().fromJson(data, Users::class.java)
                            SendMoney(remember, activity, dataPoint)
                        }
                    }
                }
                snackMessage?.let {
                    if (it == "Success") {
                        remember.navigateUp()
                    }
                    LaunchedEffect(key1 = it) {
                        coroutine.launch {
                            try {
                                snackBar.currentSnackbarData?.dismiss()
                                snackBar.showSnackbar(
                                    message = "$snackMessage"
                                )
                            } catch (e: Exception) {
                                Log.i("shjdsf", "${e.message}")
                            }
                        }
                    }
                }
                loaderBoolean?.let {
                    if (it) {
                        Dialog(onDismissRequest = {

                        }) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    Color.White
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.Black
                                    )
                                    Text(text = "Loading...", color = Color.Black)

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SomeThing(remember: NavHostController?, context: Context, shared: SharedPreferences) {
        var dialog by remember {
            mutableStateOf(false)
        }
        if (dialog) {
            AlertDialog(onDismissRequest = {
                dialog = false
            }, confirmButton = {
                Button(onClick = {
                    dialog = false
                    shared.edit().clear().apply()


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
                        dialog = true
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
    }

}

