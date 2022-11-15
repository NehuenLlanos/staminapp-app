package com.staminapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staminapp.data.repository.FavouriteRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.util.SessionManager
import kotlinx.coroutines.launch

class HomeViewModel (
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    fun getUserRoutines() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.getUserRoutines()
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

    fun getFavourites() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            favouriteRepository.getFavourites()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                favouriteRoutines = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false
            )
        }
    }
}