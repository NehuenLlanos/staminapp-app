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

    private val currentRoutineMutex = Mutex()
    private var currentRoutine: Routine? = null


    private val currentCyclesForRoutineMutex = Mutex()
    private var currentCyclesForRoutineList: List<Cycle> = emptyList()
    private var lastRoutineId: Int = -1

    private val currentExercisesForCycleMutex = Mutex()
    private var currentExercisesForCycleList: List<Exercise> = emptyList()
    private var lastCycleId: Int = -1


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
        return currentRoutinesMutex.withLock { this.currentRoutinesList }
    }

    suspend fun getRoutine(refresh: Boolean = false, id: Int) : Routine {
        if (refresh || currentRoutine == null || currentRoutine?.id != id) {
            val result = remoteDataSource.getRoutine(id)
            currentRoutineMutex.withLock {
                this.currentRoutine = result.asModel()
            }
        }
        return currentRoutineMutex.withLock { this.currentRoutine!! }
    }

    suspend fun getCyclesForRoutine(refresh: Boolean = false, id: Int) : List<Cycle> {
        if (refresh || currentCyclesForRoutineList.isEmpty() || lastRoutineId != id) {
            val result = remoteDataSource.getCyclesForRoutine(id)
            currentCyclesForRoutineMutex.withLock {
                var cycles : MutableList<Cycle> = mutableListOf()
                result.content.forEach{
                    cycles.add(it.asModel())
                }
                this.lastRoutineId = id
                this.currentCyclesForRoutineList = cycles
            }
        }
        return currentCyclesForRoutineMutex.withLock { this.currentCyclesForRoutineList }
    }

    suspend fun getExerciseForCycle(refresh: Boolean = false, id: Int) : List<Exercise> {
        if (refresh || currentExercisesForCycleList.isEmpty() || lastCycleId != id) {
            val result = remoteDataSource.getExercisesForCycle(id)
            currentExercisesForCycleMutex.withLock {
                var exercises : MutableList<Exercise> = mutableListOf()
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