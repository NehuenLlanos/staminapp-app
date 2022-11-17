package com.staminapp.ui.main

import android.content.Intent
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.staminapp.MyApplication
import com.staminapp.ui.execute.*
import com.staminapp.ui.explore.ExploreScreen
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

    object Routine: Destination("routine/{id}") {
        fun createRoute(routineId: Int) = "routine/$routineId"
    }

    object ExecutionPreviewRoutine: Destination("routine/execute/{id}") {
        fun createRoute(routineId: Int) = "routine/execute/$routineId"
    }
    object ExecutionRoutineScreen: Destination("routine/execute/exercise-preview/{id}") {
        fun createRoute(routineId: Int) = "routine/execute/exercise-preview/$routineId"
    }

    object ExecutionFinishedRoutine: Destination("routine/execute/routine-finished/{id}/{totalTime}") {
        fun createRoute(routineId: Int, totalTime : Int) = "routine/execute/routine-finished/$routineId/$totalTime"
    }

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

    val isAuthenticated = (ctx.applicationContext as MyApplication).sessionManager.loadAuthToken() != null

    NavHost(navController = navController, startDestination = if (isAuthenticated) Destination.Home.route else Destination.SignIn.route) {

        composable(Destination.SignIn.route) {
            SignInScreen(navController)
        }

        composable(Destination.Home.route) {
            MainScaffold(
                selectedIndex = 0,
                navController = navController
            ) { modifier, navController ->
                HomeScreen(modifier, navController)
            }
        }

        composable(Destination.Explore.route) {
            MainScaffold(
                selectedIndex = 1,
                navController = navController
            ) { modifier, navController ->
                ExploreScreen(modifier, navController)
            }
        }

        composable(Destination.Profile.route) {
            MainScaffold(
                selectedIndex = 2,
                navController = navController
            ) { modifier, navController ->
                ProfileScreen(modifier, navController)
            }
        }

        composable(
            route = Destination.Routine.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.staminapp.com/routine/{id}"
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = "https://staminapp.com/routine/{id}"
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = "http://www.staminapp.com/routine/{id}"
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = "http://staminapp.com/routine/{id}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            val id = entry.arguments?.getInt("id")
            if (id == null || id == -1) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                RoutineScreen(id, isAuthenticated, navController)
            }
        }

        composable(route = Destination.ExecutionPreviewRoutine.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
        )) { entry ->
            val id = entry.arguments?.getInt("id")
            if (id == null || id == -1) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                StartExecutionScreen(id, navController)
            }
        }

        composable(route = Destination.ExecutionRoutineScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) { entry ->
            val id = entry.arguments?.getInt("id")
            if (id == null || id == -1) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                Execution(id, navController)
            }
        }

        composable(route = Destination.ExecutionFinishedRoutine.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("totalTime") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) { entry ->
            val id = entry.arguments?.getInt("id")
            val totalTime = entry.arguments?.getInt("totalTime")
            if (id == null || id == -1 || totalTime == null) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                FinishedExecutionScreen(id, totalTime, navController)
            }
        }
    }
}
