package com.staminapp.ui.execute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.ui.explore.ExploreUiState
import com.staminapp.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

class ExecuteViewModel (
    private val sessionManager: SessionManager,
    private val reviewRepository: ReviewRepository,
    private val routineRepository: RoutineRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ExecuteUiState())
        private set


    /* Variables for Execution Routine 1 */
    private val _progressBar = MutableStateFlow(0f)
    val progressBar = _progressBar.asStateFlow()

    fun setProgressBarInc(value: Float) {
        _progressBar.value += value
    }

    fun setProgressBarDec(value: Float) {
        _progressBar.value -= value
    }

//    private val _progressBarInc = MutableStateFlow(0f)
//    val progressBarInc = _progressBarInc.asStateFlow()
//
//    fun setProgressBarInc(value: Float) {
//        _progressBarInc.value += value
//    }


    /* Variables for Execution Routine 2 */
    val exercisesList :MutableList<Exercise> = mutableListOf()

    private val _exercisesListIndex = MutableStateFlow(0)
    val exercisesListIndex = _exercisesListIndex.asStateFlow()

    fun setExercisesListIndex(index: Int) {
        _exercisesListIndex.value = index
    }

    val cyclesList :MutableList<Cycle> = mutableListOf()

    private val _cyclesListIndex = MutableStateFlow(0)
    val cyclesListIndex = _cyclesListIndex.asStateFlow()

    fun setCyclesListIndex(index: Int) {
        _cyclesListIndex.value = index
    }

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

class Pair (exercise : Exercise, cycle : Cycle){
    var exerciseIn : Exercise = exercise
        get() : Exercise {
            return exerciseIn
        }
    var cycleIn : Cycle = cycle
        get() : Cycle {
            return cycleIn
        }
}