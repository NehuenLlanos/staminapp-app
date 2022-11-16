package com.staminapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.staminapp.MyApplication

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
    val routineRepository = application.routineRepository
    return ExecuteViewModelFactory(sessionManager, reviewRepository, routineRepository, LocalSavedStateRegistryOwner.current, defaultArgs)
}

fun decodeBase64Image(encodedString: String?): Bitmap {
    if (encodedString == null) {
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }
    val imageBytes = Base64.decode(encodedString.split(',')[1], Base64.NO_PADDING)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}