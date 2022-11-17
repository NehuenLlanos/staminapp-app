package com.staminapp.ui.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.staminapp.ui.main.Destination
import com.staminapp.data.repository.UserRepository
import com.staminapp.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel (
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _failLogin = MutableStateFlow(false)
    val failLogin = _failLogin.asStateFlow()

    fun setName(name: String) {
        _name.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setFail(fail: Boolean) {
        _failLogin.value = fail
    }

    var uiState by mutableStateOf(
        SignInUiState(isAuthenticated = sessionManager.loadAuthToken() != null, username = TextFieldValue(""))
    )
        private set

    fun login(username: String, password: String, navController: NavHostController, after: String?) = viewModelScope.launch {
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
            navController.backQueue.clear()
            if ((after ?: "") == "") {
                navController.navigate(Destination.Home.route)
            } else {
                navController.navigate(after!!)
            }
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false,
            )
            setFail(true)
        }
    }
}