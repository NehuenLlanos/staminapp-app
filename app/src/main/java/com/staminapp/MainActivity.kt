package com.staminapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.theme.StaminappAppTheme

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object Profile: Destination("profile")
    object List: Destination("list")
    object Routine: Destination("routine/{elementId}") {
        fun createRoute(elementId: Int) = "routine/$elementId"
    }
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

    NavHost(navController = navController, startDestination = "home") {
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen() }
        composable(Destination.List.route) { ListScreen(navController) }
        composable(Destination.Routine.route) { navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            if (elementId == null ) {
                Toast.makeText(ctx, "ElementId is required", Toast.LENGTH_SHORT).show()
            } else {
                RoutineScreen(elementId = elementId.toInt())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StaminappAppTheme {
        Greeting("Android")
    }
}