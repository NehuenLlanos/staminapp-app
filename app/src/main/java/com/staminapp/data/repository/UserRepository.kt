package com.staminapp.data.repository


import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine
import com.staminapp.data.model.User
import com.staminapp.data.network.UserRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository (
    private val remoteDataSource: UserRemoteDataSource
){
    private val currentUserMutex = Mutex()
    private var currentUser: User? = null
    private var currentUserLastFetch: Long = 0

    private val userRoutinesMutex = Mutex()
    private var userRoutinesList: List<Routine> = emptyList()
    private var userRoutinesLastFetch: Long = 0

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun getCurrentUser() : User? {
        val now = System.currentTimeMillis()
        if (currentUser == null || now - currentUserLastFetch > 120000) {
            currentUserLastFetch = now
            val result = remoteDataSource.getCurrentUser()
            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }
        return currentUserMutex.withLock { this.currentUser }
    }

    suspend fun getUserRoutines() : List<Routine> {
        val now = System.currentTimeMillis()
        if (userRoutinesList.isEmpty() || now - userRoutinesLastFetch > 120000) {
            userRoutinesLastFetch = now
            val result = remoteDataSource.getUserRoutines()
            userRoutinesMutex.withLock {
                val routines : MutableList<Routine> = mutableListOf()
                result.content.forEach{
                    routines.add(it.asModel())
                }
                this.userRoutinesList = routines
            }
        }
        return userRoutinesMutex.withLock { this.userRoutinesList }
    }
}