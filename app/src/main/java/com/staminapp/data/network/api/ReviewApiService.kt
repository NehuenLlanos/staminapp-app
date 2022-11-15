package com.staminapp.data.network.api

import com.staminapp.data.network.model.NetworkCredentials
import com.staminapp.data.network.model.NetworkReview
import com.staminapp.data.network.model.NetworkToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApiService {

    @POST("reviews/{routineId}")
    suspend fun rate(@Path("routineId") id: Int, @Body review: NetworkReview) : Response<Unit>
}