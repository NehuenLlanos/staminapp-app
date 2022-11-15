package com.staminapp.data.network.api

import com.staminapp.data.network.model.NetworkPagedContent
import com.staminapp.data.network.model.NetworkRoutine
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavouriteApiService {
    @GET("favourites")
    suspend fun getFavourites(@Query("size") size: Int?= MAX_SIZE) : Response<NetworkPagedContent<NetworkRoutine>>

    @POST("favourites/{routineId}")
    suspend fun putFavourite(@Path("routineId") id: Int) : Response<Unit>

    @DELETE("favourites/{routineId}")
    suspend fun deleteFavourite(@Path("routineId") id: Int) : Response<Unit>

    companion object {
        const val MAX_SIZE = Int.MAX_VALUE
    }
}