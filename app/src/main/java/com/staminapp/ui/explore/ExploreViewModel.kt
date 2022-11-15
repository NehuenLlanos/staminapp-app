package com.staminapp.ui.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.home.HomeUiState
import com.staminapp.util.SessionManager
import kotlinx.coroutines.launch

class ExploreViewModel (
    private val sessionManager: SessionManager,
    private val routineRepository: RoutineRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ExploreUiState())
        private set

    fun getAllRoutines() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getAllRoutines()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                routines = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }
}