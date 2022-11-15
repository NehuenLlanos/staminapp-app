package com.staminapp.ui.execute

import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine

data class ExecuteUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routine : Routine? = null,
    val cycles : List<Cycle>? = null,
    val exercises : MutableMap<Int, List<Exercise>>? = mutableMapOf(),
    val score : Int? = null,
)