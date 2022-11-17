package com.staminapp.ui.explore

import androidx.compose.ui.res.stringResource
import com.staminapp.R
import com.staminapp.data.model.Routine

data class ExploreUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routines : List<Routine>? = null,
    val displayedRoutines : List<Routine>? = null,
    val selectedDifficulties : MutableList<Boolean> = mutableListOf(false, false, false, false, false) // rookie, beginner, intermediate, advanced, expert
)