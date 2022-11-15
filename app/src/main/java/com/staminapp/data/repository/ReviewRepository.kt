package com.staminapp.data.repository

import com.staminapp.data.network.ReviewRemoteDataSource

class ReviewRepository(
    private val remoteDataSource: ReviewRemoteDataSource
) {
    suspend fun rate(id: Int, score: Int) {
        remoteDataSource.rate(id, score)
    }
}