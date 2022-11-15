package com.staminapp.data.network.api

import com.staminapp.data.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    @POST("users/login")
    suspend fun login(@Body credential: NetworkCredentials) : Response<NetworkToken>

    // Como logout no da respuesta se pone el Unit que es vacio
    @POST("users/logout")
    suspend fun logout() : Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser() : Response<NetworkUser>

    @GET("users/current/routines")
    suspend fun getUserRoutines(@Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkRoutine>>

    companion object {
        const val MAX_SIZE = Int.MAX_VALUE
    }
}