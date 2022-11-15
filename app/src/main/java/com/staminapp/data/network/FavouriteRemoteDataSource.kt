package com.staminapp.data.network

import com.staminapp.data.network.api.FavouriteApiService
import com.staminapp.data.network.api.RoutineApiService
import com.staminapp.data.network.model.NetworkCycle
import com.staminapp.data.network.model.NetworkCycleExercise
import com.staminapp.data.network.model.NetworkPagedContent
import com.staminapp.data.network.model.NetworkRoutine
import com.staminapp.util.SessionManager

class FavouriteRemoteDataSource (
    private val sessionManager: SessionManager,
    private val favouriteApiService: FavouriteApiService
) : RemoteDataSource() {

    suspend fun getFavourites() : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse { favouriteApiService.getFavourites() }
    }

    suspend fun putFavourite(id: Int) : Unit {
        return handleApiResponse { favouriteApiService.putFavourite(id) }
    }

    suspend fun deleteFavourite(id: Int) : Unit {
        return handleApiResponse { favouriteApiService.deleteFavourite(id) }
    }
}