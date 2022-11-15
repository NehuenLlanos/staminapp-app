package com.staminapp.data.network.api

import android.util.Size
import com.staminapp.data.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutinesApiService {

    @GET("routines")
    suspend fun getAllRoutines(@Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") id: Int) : Response<NetworkRoutine>

    companion object {
        const val MAX_SIZE = Int.MAX_VALUE
    }
}

