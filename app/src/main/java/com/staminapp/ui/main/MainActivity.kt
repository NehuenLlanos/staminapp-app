package com.staminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.main.MainViewModel
import com.staminapp.ui.theme.StaminappAppTheme
import com.staminapp.util.getViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.ui.main.canGetCurrentUser


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
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    @StringRes resId: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(resId),
            modifier = Modifier.padding(8.dp))
    }
}
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!uiState.isAuthenticated) {
            ActionButton(
                resId = "Logueate",
                onClick = {
                    viewModel.login("johndoe", "1234567890")
                })
        } else {
            ActionButton(
                resId = "Deslogueate PETE",
                onClick = {
                    viewModel.logout()
                })
        }

        ActionButton(
            resId = "Obtener el user de ahora",
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getCurrentUser()
            })
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentUserData = uiState.currentUser?.let {
                "Current User: ${it.firstName} ${it.lastName} (${it.email})"
            }
            Text(
                text = currentUserData?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            Text(
                text = uiState.message?: "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {
    val ctx = LocalContext.current

    NavHost(navController = navController, startDestination = "sign-in") {
        composable(Destination.SignIn.route) { SignInScreen(navController) }
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen(navController) }
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
        composable(Destination.ExerciseScreenReps.route) { ExerciseScreenReps() }
        composable(Destination.ExerciseScreenRepsAndTime.route) { ExerciseScreenRepsAndTime() }
        composable(Destination.ExerciseScreenFinished.route) { ExerciseScreenFinished() }
    }
}
