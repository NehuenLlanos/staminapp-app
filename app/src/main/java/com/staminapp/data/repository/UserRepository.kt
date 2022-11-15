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

    private val userRoutinesMutex = Mutex()
    private var userRoutinesList: List<Routine> = emptyList()

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun getCurrentUser(refresh: Boolean) : User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()
            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }
        return currentUserMutex.withLock { this.currentUser }
    }

    suspend fun getUserRoutines(refresh: Boolean = false) : List<Routine> {
        if (refresh || userRoutinesList.isEmpty()) {
            val result = remoteDataSource.getUserRoutines()
            userRoutinesMutex.withLock {
                var routines : MutableList<Routine> = mutableListOf()
                result.content.forEach{
                    routines.add(it.asModel())
                }
                this.userRoutinesList = routines
            }
        }
        return userRoutinesMutex.withLock { this.userRoutinesList }
    }
}