package com.staminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.staminapp.ui.theme.StaminappAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaminappAppTheme {
                SignInCard()
            }
        }
    }
}

@Composable
fun SignInCard() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(24.dp)) {
            UsernameTextField()
            PasswordTextField()
            SignInButton()
        }
    }
}

@Composable
fun UsernameTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Surface(modifier = Modifier.padding(24.dp)) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Email") },
        )
    }
}

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Surface(modifier = Modifier.padding(24.dp)) {
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text(text = "Contrasenia") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}

@Composable
fun SignInButton() {
    Surface(modifier = Modifier.padding(24.dp)) {
        Button(
            onClick = {
            //your onclick code
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(50.dp)) {
            Text(text = "Ingresar",color = MaterialTheme.colors.background)

        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StaminappAppTheme {
        SignInCard()
    }
}