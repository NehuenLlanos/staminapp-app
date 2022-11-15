package com.staminapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.staminapp.data.repository.FavouriteRepository
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.ui.routines.RoutineViewModel

class RoutineViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val routineRepository: RoutineRepository,
    private val favouriteRepository: FavouriteRepository,
    private val reviewRepository: ReviewRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(RoutineViewModel::class.java) ->
                RoutineViewModel(sessionManager, routineRepository, favouriteRepository, reviewRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}