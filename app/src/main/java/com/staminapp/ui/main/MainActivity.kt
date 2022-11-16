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
import com.staminapp.ui.execute.*
import com.staminapp.ui.home.HomeScreen
import com.staminapp.ui.profile.ProfileScreen
import com.staminapp.ui.routines.RoutineScreen
import com.staminapp.ui.signIn.SignInScreen
import com.staminapp.ui.theme.StaminappAppTheme


sealed class Destination(val route: String) {
    object SignIn: Destination("sign-in")
    object Home: Destination("home")
    object Profile: Destination("profile")
//    object List: Destination("list")
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
//                    SignInScreen(navController = navController)
                }
            }
        }
    }
}

//@Composable
//fun ActionButton(
//    str: String,
//    enabled: Boolean = true,
//    onClick: () -> Unit
//) {
//    Button(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
//        enabled = enabled,
//        onClick = onClick,
//    ) {
//        Text(
//            text = str,
//            modifier = Modifier.padding(8.dp))
//    }
//}
//@Composable
//fun MainScreen(
//    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
//) {
//    val uiState = viewModel.uiState
//
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        if (!uiState.isAuthenticated) {
//            ActionButton(
//                str = "Logueate",
//                onClick = {
//                    viewModel.login("nehuen", "1234")
//                })
//        } else {
//            ActionButton(
//                str = "Deslogueate PETE",
//                onClick = {
//                    viewModel.logout()
//                })
//        }
//
//        ActionButton(
//            str = "Obtener el user de ahora",
//            enabled = uiState.canGetCurrentUser,
//            onClick = {
//                viewModel.getCurrentUser()
//            })
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val currentUserData = uiState.currentUser?.let {
//                "Current User: ${it.firstName} ${it.lastName} (${it.email})"
//            }
//            Text(
//                text = currentUserData ?: "",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
//                fontSize = 18.sp,
//                color = Color.Black
//            )
//            Text(
//                text = uiState.message?: "",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
//                fontSize = 18.sp,
//                color = Color.Black
//            )
//        }
//    }
//}

@Composable
fun NavigationAppHost(navController: NavHostController) {
    val ctx = LocalContext.current

    NavHost(navController = navController, startDestination = "sign-in") {
        composable(Destination.SignIn.route) { SignInScreen(navController = navController) }
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen(navController = navController) }
//        composable(Destination.List.route) { ListScreen(navController) }
        composable(Destination.ExecuteRoutine.route) { StartExecutionScreen() }
        composable(Destination.Routine.route) { navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            if (elementId == null) {
                Toast.makeText(ctx, "ERROR FATAL. Volver a correr la aplicación", Toast.LENGTH_SHORT).show()
            } else {
                RoutineScreen(elementId.toInt(), navController)
            }
        }
        composable(Destination.ExercisePreview.route) { ExercisePreview() }
        composable(Destination.ExerciseScreenTime.route) { ExerciseScreenTime() }
        composable(Destination.ExerciseScreenReps.route) { ExerciseScreenReps() }
        composable(Destination.ExerciseScreenRepsAndTime.route) { ExerciseScreenRepsAndTime() }
        composable(Destination.ExerciseScreenFinished.route) { ExerciseScreenFinished() }
    }
}
