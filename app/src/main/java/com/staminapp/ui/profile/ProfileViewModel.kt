package com.staminapp.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.explore.ExploreUiState
import com.staminapp.ui.main.Destination
import com.staminapp.util.SessionManager
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun getCurrentUser() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.getCurrentUser()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                currentUser = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun logout(navController: NavController) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.logout()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                currentUser = null,
            )
            navController.backQueue.clear()
            navController.navigate(Destination.SignIn.withoutAfter())
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }
}