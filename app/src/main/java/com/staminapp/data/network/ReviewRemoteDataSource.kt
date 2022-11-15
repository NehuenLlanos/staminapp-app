package com.staminapp.data.network

import com.staminapp.data.network.api.ReviewApiService
import com.staminapp.data.network.api.UserApiService
import com.staminapp.data.network.model.NetworkCredentials
import com.staminapp.data.network.model.NetworkReview
import com.staminapp.util.SessionManager

class ReviewRemoteDataSource (
    private val sessionManager: SessionManager,
    private val reviewApiService: ReviewApiService
) : RemoteDataSource() {
    suspend fun rate(routineId: Int, score: Int) {
        val response = handleApiResponse {
            reviewApiService.rate(routineId, NetworkReview(score, ""))
        }
    }
}