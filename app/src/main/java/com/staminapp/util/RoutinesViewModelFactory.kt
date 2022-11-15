package com.staminapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.staminapp.data.repository.RoutinesRepository
import com.staminapp.ui.main.MainViewModel
import com.staminapp.ui.routines.RoutinesViewModel

class RoutinesViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val routinesRepository: RoutinesRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(RoutinesViewModel::class.java) ->
                RoutinesViewModel(sessionManager, routinesRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}