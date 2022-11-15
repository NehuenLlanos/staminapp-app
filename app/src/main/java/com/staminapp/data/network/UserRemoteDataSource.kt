package com.staminapp.data.network

import com.staminapp.data.network.api.UserApiService
import com.staminapp.data.network.model.NetworkCredentials
import com.staminapp.data.network.model.NetworkPagedContent
import com.staminapp.data.network.model.NetworkRoutine
import com.staminapp.data.network.model.NetworkUser
import com.staminapp.util.SessionManager

class UserRemoteDataSource (
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {
    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            userApiService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse {
            userApiService.logout()
        }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser() : NetworkUser {
        return handleApiResponse { userApiService.getCurrentUser() }
    }

    suspend fun getUserRoutines() : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse { userApiService.getUserRoutines() }
    }
}