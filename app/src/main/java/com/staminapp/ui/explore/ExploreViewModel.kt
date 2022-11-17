package com.staminapp.ui.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.model.Routine
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.home.HomeUiState
import com.staminapp.util.SessionManager
import com.staminapp.util.getDifficultyApiStringFromIndex
import com.staminapp.util.translateDifficutlyForApi
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
                routines = response,
                displayedRoutines = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun selectDifficulty(diff : Int) {
        val newList: MutableList<Routine> = mutableListOf()
        uiState.selectedDifficulties[diff] = true
        val difficulties: MutableList<String> = mutableListOf()
        uiState.selectedDifficulties.forEachIndexed { index, entry ->
            if (entry) {
                difficulties.add(getDifficultyApiStringFromIndex(index))
            }
        }
        for (routine in uiState.routines!!) {
            if (routine.difficulty in difficulties) {
                newList.add(routine)
            }
        }
        uiState = uiState.copy(
            displayedRoutines = newList,
            selectedDifficulties = uiState.selectedDifficulties
        )
    }

    fun unselectDifficulty(diff : Int) {
        uiState.selectedDifficulties[diff] = false
        val newList: MutableList<Routine> = mutableListOf()
        var allFalse = true
        for (entry in uiState.selectedDifficulties) {
            if (entry) {
                allFalse = false;
            }
        }
        if (allFalse) {
            newList.addAll(uiState.routines!!)
        } else {
            newList.addAll(uiState.displayedRoutines!!)
            for (routine in uiState.displayedRoutines!!) {
                if (routine.difficulty == getDifficultyApiStringFromIndex(diff)) {
                    newList.remove(routine)
                }
            }
        }
        uiState = uiState.copy(
            displayedRoutines = newList,
            selectedDifficulties = uiState.selectedDifficulties
        )
    }
}