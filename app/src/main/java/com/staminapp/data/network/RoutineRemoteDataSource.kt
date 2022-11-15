package com.staminapp.data.network

import com.staminapp.data.network.api.RoutineApiService
import com.staminapp.data.network.model.NetworkCycle
import com.staminapp.data.network.model.NetworkCycleExercise
import com.staminapp.data.network.model.NetworkPagedContent
import com.staminapp.data.network.model.NetworkRoutine
import com.staminapp.util.SessionManager

class RoutineRemoteDataSource (
    private val sessionManager: SessionManager,
    private val routineApiService: RoutineApiService
) : RemoteDataSource() {

    suspend fun getAllRoutines() : NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse { routineApiService.getAllRoutines() }
    }

    suspend fun getRoutine(id: Int) : NetworkRoutine{
        return handleApiResponse { routineApiService.getRoutine(id) }
    }

    suspend fun getCyclesForRoutine(id: Int) : NetworkPagedContent<NetworkCycle>{
        return handleApiResponse { routineApiService.getCyclesForRoutine(id) }
    }

    suspend fun getExercisesForCycle(id: Int) : NetworkPagedContent<NetworkCycleExercise>{
        return handleApiResponse { routineApiService.getExercisesForCycle(id) }
    }

}