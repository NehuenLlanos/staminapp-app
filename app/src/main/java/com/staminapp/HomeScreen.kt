package com.staminapp

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.routines.RoutinesViewModel
import com.staminapp.ui.theme.StaminappAppTheme
import com.staminapp.util.getViewModelFactory
import kotlinx.coroutines.selects.select
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.data.model.Routine
import com.staminapp.util.getRoutinesViewModelFactory


@Composable
fun HomeScreen(navController: NavController) {
    HomeScaffold(navController)
}
@Composable
fun HomeScaffold(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar {
                Image(
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(1f),
                    painter = painterResource(id = R.drawable.logoblack),
                    contentDescription = "Logo",
                    )
            }
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigation(elevation = 10.dp) {
                    BottomNavigationItem(icon = {
                        if (selectedIndex == 0) {
                            Icon(Icons.Filled.Home, contentDescription = "Inicio")
                        } else {
                            Icon(Icons.Outlined.Home, contentDescription = "Inicio")
                        }
                    },
                        label = { Text(text = "Inicio") },
                        selected = (selectedIndex == 0),
                        onClick = {
                            selectedIndex = 0
                        })

                    BottomNavigationItem(icon = {
                        if (selectedIndex == 1) {
                            Icon(Icons.Filled.Search, contentDescription = "Inicio")
                        } else {
                            Icon(Icons.Outlined.Search, contentDescription = "Inicio")
                        }
                    },
                        label = { Text(text = "Explorar") },
                        selected = (selectedIndex == 1),
                        onClick = {
                            selectedIndex = 1
                        })

                    BottomNavigationItem(icon = {
                        if (selectedIndex == 2) {
                            Icon(Icons.Filled.Person, contentDescription = "Inicio")
                        } else {
                            Icon(Icons.Outlined.Person, contentDescription = "Inicio")
                        }
                    },
                        label = { Text(text = "Perfil") },
                        selected = (selectedIndex == 2),
                        onClick = {
                            selectedIndex = 3
//                            navController.navigate(Destination.Profile.route)
                        })
                }
            }
        },
    ) {

        val scrollState = rememberScrollState()
        HomeScreenContent(Modifier.padding(it), navController, scrollState)
//        if (selectedIndex == 0) {
//        HomeScreenContent(navController, scrollState)
//        } else if (selectedIndex == 1) {
//            Text(text = "Explorar la concha de tu madre", color = MaterialTheme.colors.primaryVariant)
//        } else if (selectedIndex == 3) {
//            ProfileScreen(navController)
//        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    scrollState: ScrollState,
    viewModel: RoutinesViewModel = viewModel(factory = getRoutinesViewModelFactory())
) {
    val uiState = viewModel.uiState
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "BUENOS DÍAS, USUARIO",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                viewModel.getRoutine(1)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(text = "Ingresar",color = MaterialTheme.colors.background)
        }
        if(uiState.currentRoutine != null) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RecentCard(navController, Modifier.weight(1f), uiState.currentRoutine)
                RecentCard(navController, Modifier.weight(1f), uiState.currentRoutine)
            }
        }

//        for(i in viewModel.uiState.routines) {
//            Row(
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//
//                RecentCard(navController, Modifier.weight(1f), i)
//                RecentCard(navController, Modifier.weight(1f), i)
//            }
//        }
//        Text(text = "Mi Biblioteca",
//            color = MaterialTheme.colors.primaryVariant,
//            fontSize = 25.sp,
//            fontWeight = FontWeight.Bold
//        )
//        for(i in 1..10) {
//            Row(
//                modifier = Modifier
//                    .padding(vertical = 4.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                RoutineCard(navController, Modifier.weight(1f))
//                RoutineCard(navController, Modifier.weight(1f))
//            }
//        }
    }
}

@Composable
fun RecentCard(navController: NavController, modifier: Modifier = Modifier, routine: Routine) {
    Card(
        modifier = modifier.clickable{navController.navigate(Destination.Routine.route)},
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(56.dp),
                    painter = painterResource(id = R.drawable.tincho2),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = routine.name,
                    color = MaterialTheme.colors.background,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,

                )
            }
        }

    }
}

@Composable
fun RoutineCard(navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable{navController.navigate(Destination.Routine.route)},
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)) {
            Image(
                painter = painterResource(id = R.drawable.tincho2),
                contentDescription = "Routine",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = "Routine Name",
                    color = MaterialTheme.colors.background,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}


