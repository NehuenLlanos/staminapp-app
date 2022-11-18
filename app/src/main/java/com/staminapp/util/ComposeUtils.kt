package com.staminapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.staminapp.MyApplication
import com.staminapp.R
import java.io.File
import java.io.FileOutputStream


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

@Composable
fun translateDifficultyForApp(string: String): String {
    val dict = mapOf(
        Pair("rookie", stringResource(R.string.rookie)),
        Pair("beginner", stringResource(R.string.beginner)),
        Pair("intermediate", stringResource(R.string.intermediate)),
        Pair("advanced", stringResource(R.string.advanced)),
        Pair("expert", stringResource(R.string.expert)),
    )

    return dict.getOrDefault(string.lowercase(), "")
}

@Composable
fun translateDifficutlyForApi(string: String): String {
    val dict = mapOf(
        Pair(stringResource(R.string.rookie), "rookie"),
        Pair(stringResource(R.string.beginner), "beginner"),
        Pair(stringResource(R.string.intermediate), "intermediate"),
        Pair(stringResource(R.string.advanced), "advanced"),
        Pair(stringResource(R.string.expert), "expert"),
    )

    return dict.getOrDefault(string.lowercase(), "")
}

fun getDifficultyApiStringFromIndex(index: Int): String {
    val dict = mapOf(
        Pair(0, "rookie"),
        Pair(1, "beginner"),
        Pair(2, "intermediate"),
        Pair(3, "advanced"),
        Pair(4, "expert"),
    )

    return dict.getOrDefault(index, "")
}

fun getQrCodeBitmap(url: String): Bitmap {
    val size = 256 //pixels
    val hintMap = mapOf(EncodeHintType.MARGIN to 1)
    val code = QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, size, size, hintMap)
    val bitmap = Bitmap.createBitmap(code.width, code.height, Bitmap.Config.RGB_565)
    for (x in 0 until  code.width) {
        for (y in 0 until code.height) {
            bitmap.setPixel(x, y, if (code.get(x, y)) Color.rgb(253,153,0) else Color.rgb(0,24,51))
        }
    }
    return bitmap
}

fun saveImage(image: Bitmap, context: Context) : Uri {
    val root = context.cacheDir.toString()
    val myDir = File("$root/staminapp")
    myDir.mkdirs()

    val fName = "staminapp_image.jpg"
    val file = File(myDir, fName)
    if (file.exists()) {
        file.delete()
    }

    try {
        val out = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        file
    )
}