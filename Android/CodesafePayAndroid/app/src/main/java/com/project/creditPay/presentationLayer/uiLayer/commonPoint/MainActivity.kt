package com.project.creditPay.presentationLayer.uiLayer.commonPoint

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.CodeSafePayAndroidTheme
import com.project.creditPay.presentationLayer.uiLayer.userMain.UserMain
import com.project.creditPay.presentationLayer.viewModels.CommonViewModel
import com.project.creditPay.responsiveLayer.models.Users
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: CommonViewModel by viewModels()

    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeSafePayAndroidTheme {
                val snack = rememberCoroutineScope()
                val host = remember {
                    SnackbarHostState()
                }
                Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
                    SnackbarHost(hostState = host)
                }) { _ ->
                    var stateOf by remember {
                        mutableStateOf(false)
                    }
                    var string by remember {
                        mutableStateOf<String?>(null)
                    }
                    var loginResponse by remember {
                        mutableStateOf<ArrayList<Users>?>(null)
                    }
                    viewModel.loader.collectAsState().value.let {
                        it.condition?.let {
                            stateOf = it
                        }
                        it.message?.let {
                            string = it
                        }
                        viewModel.loader.value = LoadersResults()
                    }
                    viewModel.detailsOfLogin.collectAsState().value?.let {
                        loginResponse = it
                        viewModel.detailsOfLogin.value = null
                    }

                    loginResponse?.let {
                        if (it.isNotEmpty()) {

                            it[0].let { the ->
                                getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                    putString("id", the.id)
                                    putString("name", the.name)
                                    putString("mobile", the.mobile)
                                    putString("password", the.password)
                                    apply()
                                    finish()
                                    startActivity(
                                        Intent(
                                            applicationContext, UserMain::class.java
                                        )
                                    )
                                    it.clear()
                                }
                            }

                        }
                    }


                    if (stateOf) {
                        Dialog(onDismissRequest = {
                            stateOf = false
                        }) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    Color.White
                                ),
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
                    if (string != null) {
                        LaunchedEffect(key1 = Unit) {
                            snack.launch { host.showSnackbar(message = "$string") }
                        }
                    }

                    PointerVerify(commonViewModel = viewModel)
                }
            }
        }
    }
}



