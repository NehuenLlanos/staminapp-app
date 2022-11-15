package com.staminapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.staminapp.data.repository.RoutineRepository
import com.staminapp.data.repository.UserRepository
import com.staminapp.ui.profile.ProfileViewModel
import com.staminapp.ui.routines.RoutinesViewModel

class ProfileViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(sessionManager, userRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}