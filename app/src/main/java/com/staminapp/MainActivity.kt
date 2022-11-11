package com.staminapp

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
import com.staminapp.ui.theme.StaminappAppTheme


sealed class Destination(val route: String) {
    object SignIn: Destination("sign-in")
    object Home: Destination("home")
    object Profile: Destination("profile")
//    object List: Destination("list")
    object Routine: Destination("routine/{elementId}") {
        fun createRoute(elementId: Int) = "routine/$elementId"
    }
    object ExecuteRoutine: Destination("routine/execute")
    object ExercisePreview: Destination("routine/execute/exercise-preview")
    object ExerciseScreenTime: Destination("rotuine/execute/exercise")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaminappAppTheme {
                // A surface container using the 'background' color from the theme
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
        composable(Destination.SignIn.route) { SignInScreen(navController) }
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen() }
//        composable(Destination.List.route) { ListScreen(navController) }
        composable(Destination.Routine.route) { RoutineScreen(navController) }
        composable(Destination.ExecuteRoutine.route) { StartExecutionScreen() }
//        composable(Destination.Routine.route) { navBackStackEntry ->
//            val elementId = navBackStackEntry.arguments?.getString("elementId")
//            if (elementId == null) {
//                Toast.makeText(ctx, "ElementId is required", Toast.LENGTH_SHORT).show()
//            } else {
//                RoutineScreen(elementId = elementId.toInt())
//            }
//        }
        composable(Destination.ExercisePreview.route) { ExercisePreview() }
        composable(Destination.ExerciseScreenTime.route) { ExerciseScreenTime() }
    }
}
