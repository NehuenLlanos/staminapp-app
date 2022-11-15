package com.staminapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.explore.ExploreViewModel
import com.staminapp.ui.home.HomeViewModel

class ExploreViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val routineRepository: RoutineRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(ExploreViewModel::class.java) ->
                ExploreViewModel(sessionManager, routineRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}