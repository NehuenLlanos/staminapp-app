package com.staminapp.ui.routines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.staminapp.Destination
import com.staminapp.data.repository.FavouriteRepository
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.util.SessionManager
import kotlinx.coroutines.launch

class RoutineViewModel (
    private val sessionManager: SessionManager,
    private val routineRepository: RoutineRepository,
    private val favouriteRepository: FavouriteRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    var uiState by mutableStateOf(RoutineUiState())
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

    fun isFavourite() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            favouriteRepository.getFavourites()
        }.onSuccess { response ->
            if (uiState.routine != null) {
                uiState = uiState.copy(
                    isFetching = false,
                    isFavourite = response.contains(uiState.routine)
                )
            }
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false
            )
        }
    }

    fun putFavourite(id : Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null,
        )
        runCatching {
            favouriteRepository.putFavourite(id)
        }.onSuccess { _ ->
            uiState = uiState.copy(
                isFetching = false,
                isFavourite = true,
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false,
            )
        }
    }

    fun deleteFavourite(id : Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null,
        )
        runCatching {
            favouriteRepository.deleteFavourite(id)
        }.onSuccess { _ ->
            uiState = uiState.copy(
                isFetching = false,
                isFavourite = false,
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false,
            )
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