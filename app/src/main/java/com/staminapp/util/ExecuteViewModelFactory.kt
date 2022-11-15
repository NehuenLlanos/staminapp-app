package com.staminapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.staminapp.data.repository.ReviewRepository
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.ui.execute.ExecuteViewModel
import com.staminapp.ui.explore.ExploreViewModel

class ExecuteViewModelFactory constructor(
    private val sessionManager: SessionManager,
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
            isAssignableFrom(ExecuteViewModel::class.java) ->
                ExecuteViewModel(sessionManager, reviewRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}