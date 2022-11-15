package com.staminapp.data.network.api

import com.staminapp.data.network.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutineApiService {

    @GET("routines")
    suspend fun getAllRoutines(@Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") id: Int) : Response<NetworkRoutine>

    @GET("routines/{routineId}/cycles")
    suspend fun getCyclesForRoutine(@Path("routineId") id: Int, @Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkCycle>>

    @GET("cycles/{cycleId}/exercises")
    suspend fun getExercisesForCycle(@Path("cycleId") id: Int, @Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkCycleExercise>>


    companion object {
        const val MAX_SIZE = Int.MAX_VALUE
    }
}

