package com.staminapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.execute.*
import com.staminapp.ui.home.HomeScreen
import com.staminapp.ui.profile.ProfileScreen
import com.staminapp.ui.routines.RoutineScreen
import com.staminapp.ui.signIn.SignInScreen
import com.staminapp.ui.theme.StaminappAppTheme


sealed class Destination(val route: String) {
    object SignIn: Destination("sign-in")

    object Home: Destination("home")
    object Explore: Destination("explore")
    object Profile: Destination("profile")

    object Routine: Destination("routine/{elementId}") {
        fun createRoute(routineId: Int) = "routine/$routineId"
    }

    object ExecuteRoutine: Destination("routine/execute")
    object ExercisePreview: Destination("routine/execute/exercise-preview")
    object ExerciseScreenTime: Destination("routine/execute/exercise")
    object ExerciseScreenReps: Destination("routine/execute/exercise-reps")
    object ExerciseScreenRepsAndTime: Destination("routine/execute/exercise-reps-and-time")
    object ExerciseScreenFinished: Destination("routine/execute/exercise-finished")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaminappAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {
    val ctx = LocalContext.current

    NavHost(navController = navController, startDestination = "sign-in") {

        composable(Destination.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(Destination.Home.route) {
            HomeScreen(navController)
        }

        composable(Destination.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Destination.Routine.route) { navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            if (elementId == null) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                RoutineScreen(elementId.toInt(), navController)
            }
        }

        composable(Destination.ExecuteRoutine.route) {
            StartExecutionScreen()
        }
        composable(Destination.ExercisePreview.route) { ExercisePreview() }
        composable(Destination.ExerciseScreenTime.route) { ExerciseScreenTime() }
        composable(Destination.ExerciseScreenReps.route) { ExerciseScreenReps() }
        composable(Destination.ExerciseScreenRepsAndTime.route) { ExerciseScreenRepsAndTime() }
        composable(Destination.ExerciseScreenFinished.route) { ExerciseScreenFinished() }
    }
}
