package com.staminapp.ui.execute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.ui.explore.ExploreUiState
import com.staminapp.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExecuteViewModel (
    private val sessionManager: SessionManager,
    private val reviewRepository: ReviewRepository,
    private val routineRepository: RoutineRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ExecuteUiState())
        private set

    private val _progressBar = MutableStateFlow(0f)
    val progressBar = _progressBar.asStateFlow()

    fun setProgressBar() {
        _progressBar.value += _progressBarInc.value
    }

    private val _progressBarInc = MutableStateFlow(0f)
    val progressBarInc = _progressBarInc.asStateFlow()

    fun setProgressBarInc(value: Float) {
        _progressBarInc.value = value
    }

    private val _currentCycleReps = MutableStateFlow(0)
    val currentCycleReps = _currentCycleReps.asStateFlow()

    fun setCycleReps(index: Int) {
        _currentCycleReps.value = index
    }

    private val _currentCycleIndex = MutableStateFlow(0)
    val currentCycleIndex = _currentCycleIndex.asStateFlow()

    fun setCycleIndex(index: Int) {
        _currentCycleIndex.value = index
    }

    private val _currentExerciseIndex = MutableStateFlow(0)
    val currentExerciseIndex = _currentExerciseIndex.asStateFlow()

    fun setExerciseIndex(index: Int) {
        _currentExerciseIndex.value = index
    }

    val currentExercise = mutableStateOf(Exercise(0, "", "", 0, 0, 0))


    fun getRoutine(id: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isAllFetching = true,
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
            getCyclesForRoutine(id)
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
            response.forEach {
                getExercisesForCycle(it.id)
            }
            uiState = uiState.copy(
                isAllFetching = false,
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