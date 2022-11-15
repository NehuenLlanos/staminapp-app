package com.staminapp.ui.routines

import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine


data class RoutinesUiState (
    val isFetching: Boolean = false,
    val message: String? = null,
    val routines : List<Routine>,
    val currentRoutine : Routine? = null,
)

//val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
//val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
//val MainUiState.getUsername: TextFieldValue get() = username