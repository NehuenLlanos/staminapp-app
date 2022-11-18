package com.staminapp.data.repository

import com.staminapp.data.model.Routine
import com.staminapp.data.network.FavouriteRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class FavouriteRepository (
    private val remoteDataSource: FavouriteRemoteDataSource
) {
    private val favouriteRoutinesMutex = Mutex()
    private var favouriteRoutinesList: List<Routine> = emptyList()

    suspend fun getFavourites() : List<Routine> {
        val result = remoteDataSource.getFavourites()
        favouriteRoutinesMutex.withLock {
            val routines : MutableList<Routine> = mutableListOf()
            result.content.forEach{
                routines.add(it.asModel())
            }
            this.favouriteRoutinesList = routines
        }
        return favouriteRoutinesMutex.withLock { this.favouriteRoutinesList }
    }

    suspend fun putFavourite(id : Int) {
        remoteDataSource.putFavourite(id)
    }

    suspend fun deleteFavourite(id : Int) {
        remoteDataSource.deleteFavourite(id)
    }
}