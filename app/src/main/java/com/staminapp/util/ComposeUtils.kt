package com.staminapp.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.staminapp.MyApplication

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    return ViewModelFactory(sessionManager, userRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getRoutinesViewModelFactory(defaultArgs: Bundle? = null): RoutinesViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val routinesRepository = application.routineRepository
    return RoutinesViewModelFactory(sessionManager, routinesRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getRoutineViewModelFactory(defaultArgs: Bundle? = null): RoutineViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val routinesRepository = application.routineRepository
    return RoutineViewModelFactory(sessionManager, routinesRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getHomeViewModelFactory(defaultArgs: Bundle? = null): HomeViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    return HomeViewModelFactory(sessionManager, userRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}


@Composable
fun getExploreViewModelFactory(defaultArgs: Bundle? = null): ExploreViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val routineRepository = application.routineRepository
    return ExploreViewModelFactory(sessionManager, routineRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getSignInViewModelFactory(defaultArgs: Bundle? = null): SignInViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    return SignInViewModelFactory(sessionManager, userRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getProfileViewModelFactory(defaultArgs: Bundle? = null): ProfileViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    return ProfileViewModelFactory(sessionManager, userRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}