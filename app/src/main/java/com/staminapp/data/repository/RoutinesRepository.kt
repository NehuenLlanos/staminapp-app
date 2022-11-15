package com.staminapp.data.repository

import com.staminapp.data.model.Routine
import com.staminapp.data.model.User
import com.staminapp.data.network.RoutinesRemoteDataSource
import com.staminapp.data.network.model.NetworkRoutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesRepository (
    private val remoteDataSource: RoutinesRemoteDataSource
) {
    private val currentRoutinesMutex = Mutex()
    private var currentRoutinesList: List<Routine> = emptyList()

    private val currentRoutineMutex = Mutex()
    private var currentRoutine: Routine? = null


    suspend fun getAllRoutines(refresh: Boolean = false) : List<Routine> {
        if (refresh || currentRoutinesList.isEmpty()) {
            val result = remoteDataSource.getAllRoutines()
            currentRoutinesMutex.withLock {
                var routines : MutableList<Routine> = mutableListOf()
                result.content.forEach{
                    routines.add(it.asModel())
                }
                this.currentRoutinesList = routines
            }
        }
        println("INICIO DEL VECTOR EN API")
        println(this.currentRoutinesList)
        println("FINAL DEL VECTOR EN API")
        return currentRoutinesMutex.withLock { this.currentRoutinesList }
    }

    suspend fun getRoutine(id: Int) : Routine {
        return remoteDataSource.getRoutine(id).asModel()
    }
}