package com.staminapp.ui.explore

import com.staminapp.data.model.Routine

data class ExploreUiState (
    val isFetching: Boolean = false,
    val message: String? = null,

    val routines : List<Routine>? = null,
    val displayedRoutines : List<Routine>? = null,
    val selectedDifficulties : MutableMap<String, Boolean> = mutableMapOf(Pair("novato", false),
                                                                          Pair("principiante", false),
                                                                          Pair("intermedio", false),
                                                                          Pair("avanzado", false),
                                                                          Pair("experto", false))
)