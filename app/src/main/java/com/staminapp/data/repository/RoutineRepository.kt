package com.staminapp.data.repository

import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine
import com.staminapp.data.network.RoutineRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutineRepository (
    private val remoteDataSource: RoutineRemoteDataSource
) {
    private val currentRoutinesMutex = Mutex()
    private var currentRoutinesList: List<Routine> = emptyList()
    private var currentRoutineListLastFetch: Long = 0

    private val currentRoutineMutex = Mutex()
    private var currentRoutine: Routine? = null
    private var currentRoutineLastFetch: Long = 0

    private val currentCyclesForRoutineMutex = Mutex()
    private var currentCyclesForRoutineList: List<Cycle> = emptyList()
    private var lastRoutineId: Int = -1
    private var currentCyclesLastFetch: Long = 0

    private val currentExercisesForCycleMutex = Mutex()
    private var currentExercisesForCycleList: List<Exercise> = emptyList()
    private var lastCycleId: Int = -1
    private var currentExercisesLastFetch: Long = 0


    suspend fun getAllRoutines() : List<Routine> {
        val now = System.currentTimeMillis()
        if (currentRoutinesList.isEmpty() || now - currentRoutineListLastFetch > 120000) {
            currentRoutineListLastFetch = now
            val result = remoteDataSource.getAllRoutines()
            currentRoutinesMutex.withLock {
                val routines : MutableList<Routine> = mutableListOf()
                result.content.forEach{
                    routines.add(it.asModel())
                }
                this.currentRoutinesList = routines
            }
        }
        return currentRoutinesMutex.withLock { this.currentRoutinesList }
    }

    suspend fun getRoutine(id: Int) : Routine {
        val now = System.currentTimeMillis()
        if (currentRoutine == null || currentRoutine?.id != id || now - currentRoutineLastFetch > 120000) {
            currentRoutineLastFetch = now
            val result = remoteDataSource.getRoutine(id)
            currentRoutineMutex.withLock {
                this.currentRoutine = result.asModel()
            }
        }
        return currentRoutineMutex.withLock { this.currentRoutine!! }
    }

    suspend fun getCyclesForRoutine(id: Int) : List<Cycle> {
        val now = System.currentTimeMillis()
        if (currentCyclesForRoutineList.isEmpty() || lastRoutineId != id || now - currentCyclesLastFetch > 120000) {
            currentCyclesLastFetch = now
            val result = remoteDataSource.getCyclesForRoutine(id)
            currentCyclesForRoutineMutex.withLock {
                val cycles : MutableList<Cycle> = mutableListOf()
                result.content.forEach{
                    cycles.add(it.asModel())
                }
                this.lastRoutineId = id
                this.currentCyclesForRoutineList = cycles
            }
        }
        return currentCyclesForRoutineMutex.withLock { this.currentCyclesForRoutineList }
    }

    suspend fun getExerciseForCycle(id: Int) : List<Exercise> {
        val now = System.currentTimeMillis()
        if (currentExercisesForCycleList.isEmpty() || lastCycleId != id || now - currentExercisesLastFetch > 120000) {
            currentExercisesLastFetch = now
            val result = remoteDataSource.getExercisesForCycle(id)
            currentExercisesForCycleMutex.withLock {
                val exercises : MutableList<Exercise> = mutableListOf()
                result.content.forEach{
                    exercises.add(it.asModel())
                }
                this.lastCycleId = id
                this.currentExercisesForCycleList = exercises
            }
        }
        return currentExercisesForCycleMutex.withLock { this.currentExercisesForCycleList }
    }
}