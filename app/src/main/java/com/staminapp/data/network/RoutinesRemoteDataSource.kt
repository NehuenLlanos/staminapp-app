package com.staminapp.data.network

import com.staminapp.data.network.api.ApiUserService
import com.staminapp.data.network.api.RoutinesApiService
import com.staminapp.data.network.model.NetworkCycle
import com.staminapp.data.network.model.NetworkCycleExercise
import com.staminapp.data.network.model.NetworkPagedContent
import com.staminapp.data.network.model.NetworkRoutine
import com.staminapp.util.SessionManager

class RoutinesRemoteDataSource (
    private val sessionManager: SessionManager,
    private val routinesApiService: RoutinesApiService
) : RemoteDataSource() {

    suspend fun getAllRoutines() : NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse { routinesApiService.getAllRoutines() }
    }

    suspend fun getRoutine(id: Int) : NetworkRoutine{
        return handleApiResponse { routinesApiService.getRoutine(id) }
    }

    suspend fun getCyclesForRoutine(id: Int) : NetworkPagedContent<NetworkCycle>{
        return handleApiResponse { routinesApiService.getCyclesForRoutine(id) }
    }

    suspend fun getExercisesForCycle(id: Int) : NetworkPagedContent<NetworkCycleExercise>{
        return handleApiResponse { routinesApiService.getExercisesForCycle(id) }
    }

}