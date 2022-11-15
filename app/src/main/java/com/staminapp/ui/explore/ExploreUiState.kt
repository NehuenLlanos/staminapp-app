package com.staminapp.ui.explore

import com.staminapp.data.model.Routine

data class ExploreUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routines : List<Routine>? = null,
)