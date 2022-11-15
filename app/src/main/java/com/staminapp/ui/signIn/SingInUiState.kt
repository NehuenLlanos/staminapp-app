package com.staminapp.ui.signIn

import androidx.compose.ui.text.input.TextFieldValue
import com.staminapp.data.model.User

data class SignInUiState (
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val message: String? = null,
    val username: TextFieldValue,
)