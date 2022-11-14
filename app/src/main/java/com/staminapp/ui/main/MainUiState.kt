package com.staminapp.ui.main

import androidx.compose.ui.text.input.TextFieldValue
import com.staminapp.data.model.User

data class MainUiState (
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val message: String? = null,
    val username: TextFieldValue,
    var loginFail: Boolean? = false
//    val password: TextFieldValue,
)

val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
val MainUiState.getUsername: TextFieldValue get() = username
//val MainUiState.getPassword: TextFieldValue get() = password

