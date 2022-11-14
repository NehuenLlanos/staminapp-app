package com.staminapp.ui.main


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.staminapp.Destination
import com.staminapp.data.repository.UserRepository
import com.staminapp.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
) : ViewModel() {

//    private val _userName = MutableLiveData(TextFieldValue(""))
//    val userName: LiveData<TextFieldValue> = _userName
//    fun onNameChange(input: String) {
//        this._userName.value.text = input
//    }

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
//
    private val _failLogin = MutableStateFlow(false)
    val failLogin = _failLogin.asStateFlow()

    fun setName(name: String) {
        _name.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }
    fun setFail(fail: Boolean) {
//        uiState.loginFail = fail
        _failLogin.value = fail
    }


    var uiState by mutableStateOf(
        MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null, username = TextFieldValue(""))
    )
        private set

    fun login(username: String, password: String, navController: NavHostController) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null,
        )
        runCatching {
            userRepository.login(username, password)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                isAuthenticated = true,
            )
            navController.navigate(Destination.Home.route)
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false,
            )
            setFail(true)
        }
    }

    fun logout() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.logout()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                isAuthenticated = false,
                currentUser = null,
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.getCurrentUser(uiState.currentUser == null)
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


}