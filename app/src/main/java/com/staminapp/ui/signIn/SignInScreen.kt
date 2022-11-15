package com.staminapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.staminapp.ui.main.MainViewModel
import com.staminapp.util.getViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun SignInScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    navController: NavHostController
) {
    val uiState = viewModel.uiState
    val username by viewModel.name.collectAsState(initial = "")
    val password by viewModel.password.collectAsState(initial = "")
    val failLogin by viewModel.failLogin.collectAsState(false)
//    var name by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 180.dp)
                .fillMaxSize(1f)
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            if (failLogin) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.setFail(false)
                    },
                    buttons = {
                        Row(
                            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { viewModel.setFail(false) }
                            ) {
                                Text("OK")
                            }
                        }
                    },
                    title = {Text("Error")},
                    text = {Text("Chequeá que el email y la contraseña sean correctos y " +
                            "verifica que hayas confirmado tu email.")}
                )

            }
            LogoImage()
            UsernameTextField(Modifier.padding(top = 70.dp), viewModel, username)
            PasswordTextField(Modifier.padding(top = 20.dp), viewModel, password)
            SignInButton(Modifier.padding(top = 30.dp), viewModel, username, password, navController)

        }
}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(id = R.drawable.logofc),
        contentDescription = "Hola",
    )
}

@Composable
fun UsernameTextField(modifier: Modifier, viewModel: MainViewModel, username: String) {

    TextField(
        modifier = modifier,
        value = username,
        onValueChange = viewModel::setName,
        label = { Text(text = "Email") },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun PasswordTextField(modifier: Modifier, viewModel: MainViewModel, password: String) {
    TextField(
        modifier = modifier,
        value = password,
        onValueChange = viewModel::setPassword,
        label = { Text(text = "Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun SignInButton(
    modifier: Modifier,
    viewModel: MainViewModel,
    username: String,
    password: String,
    navController: NavHostController
) {
    Button(
        modifier = modifier,
        onClick = {
            viewModel.login(username, password, navController)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(text = "Ingresar",color = MaterialTheme.colors.background)
    }
}

