package com.staminapp

import android.app.Application
import com.staminapp.data.network.RoutineRemoteDataSource
import com.staminapp.data.network.UserRemoteDataSource
import com.staminapp.data.network.api.RetrofitClient
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.util.SessionManager

class MyApplication : Application() {

    private val userRemoteDataSource : UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository : UserRepository
        get() = UserRepository(userRemoteDataSource)

    private val routineRemoteDataSource : RoutineRemoteDataSource
        get() = RoutineRemoteDataSource(sessionManager, RetrofitClient.getRoutinesApiService(this))

    val routineRepository : RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)

}