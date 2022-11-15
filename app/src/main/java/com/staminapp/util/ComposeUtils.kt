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
fun getRoutineViewModelFactory(defaultArgs: Bundle? = null): RoutineViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val routinesRepository = application.routineRepository
    val favouriteRepository = application.favouriteRepository
    val reviewRepository = application.reviewRepository
    return RoutineViewModelFactory(sessionManager, routinesRepository, favouriteRepository, reviewRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

@Composable
fun getHomeViewModelFactory(defaultArgs: Bundle? = null): HomeViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    val favouriteRepository = application.favouriteRepository
    return HomeViewModelFactory(sessionManager, userRepository, favouriteRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
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

@Composable
fun getExecuteViewModelFactory(defaultArgs: Bundle? = null): ExecuteViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val reviewRepository = application.reviewRepository
    return ExecuteViewModelFactory(sessionManager, reviewRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}