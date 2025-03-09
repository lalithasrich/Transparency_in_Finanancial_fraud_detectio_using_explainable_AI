package com.project.creditPay.presentationLayer.uiLayer.commonPoint

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.creditPay.R
import com.project.creditPay.dataLayer.models.LoadersResults
import com.project.creditPay.presentationLayer.uiLayer.commonPoint.ui.theme.MyView
import com.project.creditPay.presentationLayer.viewModels.CommonViewModel


@Composable
fun PointerVerify(commonViewModel: CommonViewModel? = null) {

    var stringResource by remember {
        mutableStateOf(commonViewModel?.textChanger?.value)
    }
    commonViewModel?.textChanger?.collectAsState().let {
        stringResource = it?.value
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Card(
                colors = CardDefaults.cardColors(MyView),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                shape = RoundedCornerShape(
                    bottomEnd = 50.dp, bottomStart = 50.dp
                ),
            ) {
                if (stringResource == null) {
                    Image(
                        painter = painterResource(id = R.drawable.credit_card),
                        contentDescription = null
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.credit_card),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp)
                            .width(80.dp)
                            .height(80.dp)
                            .align(
                                Alignment.CenterHorizontally
                            )
                            .fillMaxWidth()
                    )
                    stringResource!!.split("%^").let {
                        Text(
                            text = it[0],
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit(17f, TextUnitType.Sp),
                            modifier = Modifier
                                .align(
                                    Alignment.CenterHorizontally
                                )
                                .padding(10.dp, 10.dp, 20.dp, 20.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = it[1],
                            fontWeight = FontWeight.Medium,
                            fontSize = TextUnit(13f, TextUnitType.Sp),
                            modifier = Modifier
                                .padding(10.dp, 10.dp, 20.dp, 10.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                        )
                    }


                }
            }
            Column(
                modifier = Modifier.weight(0.5f)
            ) {

            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 30.dp, 10.dp, 30.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    10.dp
                )
            ) {
                NavComposition(commonViewModel)
            }

        }

    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavComposition(commonViewModel: CommonViewModel? = null) {
    val remember = rememberNavController()
    NavHost(navController = remember, startDestination = "Home") {
        composable("Home") {
            commonViewModel?.textChanger?.value = null
            Home(commonViewModel, remember)
        }
        composable("SignUp") {
            commonViewModel?.textChanger?.value = "Sign Up%^Create a Account and make Safe Payments !!"
            SignUp(commonViewModel, remember)
        }
        composable("SignIn") {
            commonViewModel?.textChanger?.value = "Sign In%^Welcome back to Credit pay!!"
            SignIn(commonViewModel, remember)
        }
    }
}

@Composable
fun Home(modifier: CommonViewModel? = null, navBackStackEntry: NavHostController? = null) {
    Column(
        Modifier.padding(10.dp)
    ) {

        Text(
            text = "Welcome CodeSafe Pay-Android App",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .fillMaxWidth()
                .padding(10.dp),
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Safest Payment of All time",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .fillMaxWidth()
                .padding(2.dp),
            fontSize = TextUnit(12f, TextUnitType.Sp),
            fontWeight = FontWeight.Medium
        )
        Button(
            onClick = {
                navBackStackEntry?.navigate("SignIn")
            }, colors = ButtonColors(
                containerColor = MyView,
                contentColor = Color.Black,
                disabledContainerColor = MyView,
                disabledContentColor = MyView
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 30.dp, 5.dp, 5.dp)
        ) {
            Text(text = "Sign In")
        }
        Button(
            onClick = {
                navBackStackEntry?.navigate("SignUp")
            }, colors = ButtonColors(
                containerColor = MyView,
                contentColor = Color.Black,
                disabledContainerColor = MyView,
                disabledContentColor = MyView
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = "Sign up")
        }
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignIn(modifier: CommonViewModel? = null, navBackStackEntry: NavHostController? = null) {
    var mobile by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var passwordIcon by remember {
        mutableStateOf(true)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {




        TextField(
            value = mobile,
            onValueChange = {
                mobile = it
            },
            placeholder = {
                Text(text = "Mobile")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone, contentDescription = null
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.password_with_dots),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
            ),
            visualTransformation = if (passwordIcon) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordIcon = !passwordIcon }) {
                    if (passwordIcon) {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_closed),
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_open),
                            contentDescription = null
                        )
                    }
                }
            },


            )
        Button(onClick = {
            if (mobile.isEmpty()) {
                modifier?.loader?.value = LoadersResults(
                    message = "Please enter your Mobile", condition = false
                )
            } else if (password.isEmpty()) {
                modifier?.loader?.value = LoadersResults(
                    message = "Please enter your Password", condition = false
                )
            } else {
                modifier?.logIn(
                    mobile = mobile, password = password
                )
            }
        }, modifier = Modifier.padding(20.dp)) {
            Text(text = "Sign In")
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUp(modifier: CommonViewModel? = null, navBackStackEntry: NavHostController? = null) {
    var mobile by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var passwordIcon by remember {
        mutableStateOf(true)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {


        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = {
                Text(text = "Name")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person, contentDescription = null
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        TextField(
            value = mobile,
            onValueChange = {
                mobile = it
            },
            placeholder = {
                Text(text = "Mobile")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone, contentDescription = null
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.password_with_dots),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
            ),
            visualTransformation = if (passwordIcon)
                PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordIcon = !passwordIcon }) {
                    if (passwordIcon) {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_closed),
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_open),
                            contentDescription = null
                        )
                    }
                }
            })

        Button(onClick = {
            if (name.isEmpty()) {
                modifier?.loader?.value = LoadersResults("Please enter your name")
            } else if (mobile.isEmpty()) {
                modifier?.loader?.value = LoadersResults("Please enter your mobile number")
            } else if (password.isEmpty()) {
                modifier?.loader?.value = LoadersResults("Please enter your Password")
            } else {
                modifier?.signUp(name = name, mobile = mobile, password = password)
            }
        }, modifier = Modifier.padding(20.dp)) {
            Text(text = "Sign up")
        }

    }
}