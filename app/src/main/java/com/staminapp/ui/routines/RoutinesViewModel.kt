package com.staminapp.ui.routines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.staminapp.Destination
import com.staminapp.data.model.Routine
import com.staminapp.data.repository.RoutinesRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.main.MainUiState
import com.staminapp.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoutinesViewModel(
    private val sessionManager: SessionManager,
    private val routinesRepository: RoutinesRepository,
) : ViewModel() {

    private val _fail = MutableStateFlow(false)
    val fail = _fail.asStateFlow()

    fun setFail(fail: Boolean) {
        _fail.value = fail
    }


    var uiState by mutableStateOf(RoutinesUiState(routines = emptyList()))
        private set

    fun getAllRoutines() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routinesRepository.getAllRoutines()
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

    fun getRoutine(id: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routinesRepository.getRoutine(id)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                currentRoutine = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

}