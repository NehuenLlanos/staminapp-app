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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.staminapp.ui.theme.StaminappAppTheme

@Composable
fun SignInScreen(navController: NavHostController) {
//    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 180.dp)
                .fillMaxSize(1f)
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            LogoImage(Modifier)
            UsernameTextField(Modifier.padding(top = 70.dp))
            PasswordTextField(Modifier.padding(top = 20.dp))
            SignInButton(Modifier.padding(top = 30.dp), navController)
        }
//    }
}

@Composable
fun LogoImage(modifier: Modifier) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.logofc),
            contentDescription = "Hola",
        )
}

@Composable
fun UsernameTextField(modifier: Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
        },
        label = { Text(text = "Email") },
        textStyle = TextStyle(
            MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        ),
    )
}

@Composable
fun PasswordTextField(modifier: Modifier) {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordError by remember { mutableStateOf(false) }
    TextField(
        modifier = modifier,
        value = password,
        onValueChange = {
            password = it
            passwordError = false
        },
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
fun SignInButton(modifier: Modifier, navController: NavHostController) {
    Button(
        modifier = modifier,
        onClick = {
            navController.navigate(Destination.ExerciseScreenRepsAndTime.route)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(text = "Ingresar",color = MaterialTheme.colors.background)
    }
}

