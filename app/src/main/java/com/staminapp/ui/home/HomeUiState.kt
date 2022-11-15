package com.staminapp.ui.home

import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine

data class HomeUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routines : List<Routine>? = null,
    val favouriteRoutines : List<Routine>? = null
)