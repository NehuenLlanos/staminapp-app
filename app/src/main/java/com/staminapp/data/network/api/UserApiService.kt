package com.staminapp.data.network.api

import com.staminapp.data.network.model.NetworkCredentials
import com.staminapp.data.network.model.NetworkToken
import com.staminapp.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUserService {

    @POST("users/login")
    suspend fun login(@Body credential: NetworkCredentials) : Response<NetworkToken>

    // Como logout no da respuesta se pone el Unit que es vacio
    @POST("users/logout")
    suspend fun logout() : Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser() : Response<NetworkUser>
}