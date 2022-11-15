package com.staminapp.ui.execute

import com.staminapp.data.model.Routine

data class ExecuteUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val score : Int? = null,
)