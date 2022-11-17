package com.staminapp.ui.routines

import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine

data class RoutineUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routine : Routine? = null,
    val cycles : List<Cycle>? = null,
    val exercises : MutableMap<Int, List<Exercise>> = mutableMapOf(),
    val isFavourite : Boolean? = false,
    val score : Int? = null,
)