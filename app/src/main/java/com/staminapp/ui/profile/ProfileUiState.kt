package com.staminapp.ui.profile

import androidx.compose.ui.text.input.TextFieldValue
import com.staminapp.data.model.User

data class ProfileUiState (
    val isFetching: Boolean = false,
    val message: String? = null,
    val currentUser: User? = null,
)
