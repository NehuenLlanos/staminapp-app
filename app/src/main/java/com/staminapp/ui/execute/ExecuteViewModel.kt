package com.staminapp.ui.execute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.ui.explore.ExploreUiState
import com.staminapp.util.SessionManager
import kotlinx.coroutines.launch

class ExecuteViewModel (
    private val sessionManager: SessionManager,
    private val reviewRepository: ReviewRepository,
    private val routineRepository: RoutineRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ExecuteUiState())
        private set

    fun getRoutine(id: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getRoutine(id = id)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                routine = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun getCyclesForRoutine(id : Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getCyclesForRoutine(id = id)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                cycles = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun getExercisesForCycle(id : Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getExerciseForCycle(id = id)
        }.onSuccess { response ->
            uiState.exercises?.put(id, response)
            uiState = uiState.copy(
                isFetching = false,
                exercises = uiState.exercises
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun rate(id : Int, score: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null,
        )
        runCatching {
            reviewRepository.rate(id, score)
        }.onSuccess { _ ->
            uiState = uiState.copy(
                isFetching = false,
                score = score,
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false,
            )
        }
    }
}