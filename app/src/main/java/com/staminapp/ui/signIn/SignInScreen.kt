package com.staminapp.ui.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.staminapp.R
import com.staminapp.util.getSignInViewModelFactory

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    after: String? = null,
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel(factory = getSignInViewModelFactory())
) {
    val uiState = viewModel.uiState
    val username by viewModel.name.collectAsState(initial = "")
    val password by viewModel.password.collectAsState(initial = "")
    val failLogin by viewModel.failLogin.collectAsState(false)

    val (focusRequester) = FocusRequester.createRefs()

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (failLogin) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.setFail(false)
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.setFail(false)
                            }
                        ) {
                            Text("OK")
                        }
                    }
                },
                title = {
                    Text(stringResource(R.string.error))
                },
                text = {
                    Text(stringResource(R.string.login_error_message))
                }
            )
        }
        Image(
            painter = painterResource(R.drawable.logofc),
            contentDescription = stringResource(R.string.app_name),
        )
        TextField(
            modifier = Modifier.padding(top = 70.dp),
            value = username,
            onValueChange = viewModel::setName,
            label = {
                Text(text = stringResource(R.string.email))
            },
            textStyle = TextStyle(
                MaterialTheme.colors.primaryVariant,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(
                onDone = { focusRequester.requestFocus() }
            ),
            singleLine = true
        )
        TextField(
            modifier = Modifier
                .padding(top = 20.dp)
                .focusRequester(focusRequester),
            value = password,
            onValueChange = viewModel::setPassword,
            label = {
                Text(text = stringResource(R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(
                MaterialTheme.colors.primaryVariant,
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.login(username, password, navController, after) }
            ),
            singleLine = true
        )
        Button(
            modifier = Modifier.padding(top = 30.dp),
            onClick = { viewModel.login(username, password, navController, after) },
            shape = RoundedCornerShape(50), // = 50% percent
        ) {
            Text(text=stringResource(R.string.login))
        }

    }
}