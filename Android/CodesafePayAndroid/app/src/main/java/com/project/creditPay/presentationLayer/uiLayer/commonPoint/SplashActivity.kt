package com.project.creditPay.presentationLayer.uiLayer.commonPoint

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.creditPay.R
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.CodeSafePayAndroidTheme
import com.project.creditPay.presentationLayer.uiLayer.userMain.UserMain
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val id = getSharedPreferences("user", MODE_PRIVATE).getString("id", "") ?: ""
            CodeSafePayAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    LaunchedEffect(key1 = Unit) {
                        delay(500)
                    }
                    NothingNew()
                    finish()
                    if (id.isEmpty()) {
                        startActivity(
                            Intent(
                                applicationContext, MainActivity::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                applicationContext, UserMain::class.java
                            )
                        )

                    }
                }
            }
        }
    }

    @Composable
    private fun NothingNew() {
        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier.fillMaxSize()
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    painter = painterResource(id = R.drawable.credit_card),
                    contentDescription = null
                )
            }
        }
    }
}
