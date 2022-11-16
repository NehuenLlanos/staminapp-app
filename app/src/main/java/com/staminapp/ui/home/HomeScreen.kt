package com.staminapp.ui.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.Destination
import com.staminapp.R
import com.staminapp.data.model.Routine
import com.staminapp.ui.execute.ExecuteViewModel
import com.staminapp.ui.profile.ProfileScreen
import com.staminapp.util.*


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
//        HomeScreenContent(Modifier.padding(it), navController, scrollState)
        ProfileScreen(Modifier.padding(it), navController)
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
//    viewModel: RoutinesViewModel = viewModel(factory = getRoutinesViewModelFactory())
//    viewModel: RoutineViewModel = viewModel(factory = getRoutineViewModelFactory())
//    viewModel: HomeViewModel = viewModel(factory = getHomeViewModelFactory())
//    viewModel: ExploreViewModel = viewModel(factory = getExploreViewModelFactory())
//    viewModel: ProfileViewModel = viewModel(factory = getProfileViewModelFactory())
//    viewModel: RoutineViewModel = viewModel(factory = getRoutineViewModelFactory())
//    viewModel: HomeViewModel = viewModel(factory = getHomeViewModelFactory())
    viewModel: ExecuteViewModel = viewModel(factory = getExecuteViewModelFactory())
) {
//    val uiState = viewModel.uiState
//    viewModel.getAllRoutines()

//    val uiState = viewModel.uiState
//    viewModel.getRoutine(1)
//    viewModel.getCyclesForRoutine(1)
//    uiState.cycles?.forEach {
//        viewModel.getExercisesForCycle(it.id)
//    }

//    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.routines == null) {
//        viewModel.getUserRoutines()
//    }

//    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.routines == null) {
//        viewModel.getAllRoutines()
//    }

//    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.currentUser == null) {
//        viewModel.getCurrentUser()
//    }

//    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.routine == null) {
//        viewModel.getRoutine(1)
//    }

//    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.favouriteRoutines == null) {
//        viewModel.getFavourites()
//    }

    val uiState = viewModel.uiState
//    if (!uiState.isFetching && uiState.routine == null) {
//        viewModel.getRoutine(1)
//    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
//        if(uiState.routine != null) {
//            Text(text = uiState.routine.score.toString(),
//                color = MaterialTheme.colors.primaryVariant,
//                fontSize = 60.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
        Button(
            onClick = {
                viewModel.rate(1, 1)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(text = "Ratear",color = MaterialTheme.colors.background)
        }
//        if(uiState.favouriteRoutines != null) {
//            uiState.favouriteRoutines?.forEach {
//                Text(text = it.name,
//                    color = MaterialTheme.colors.primaryVariant,
//                    fontSize = 60.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }



//        Button(
//            onClick = {
//                      viewModel.putFavourite(1)
//            },
//            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
//            shape = RoundedCornerShape(50.dp)
//        ) {
//            Text(text = "ME GUSTA EL SALAME DE BERNI",color = MaterialTheme.colors.background)
//        }
//        Button(
//            onClick = {
//                viewModel.deleteFavourite(1)
//            },
//            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
//            shape = RoundedCornerShape(50.dp)
//        ) {
//            Text(text = "NO ME GUSTA EL SALAME DE BERNI",color = MaterialTheme.colors.background)
//        }

//        if(uiState.currentUser != null) {
//            Text(text = uiState.currentUser?.username,
//                color = MaterialTheme.colors.primaryVariant,
//                fontSize = 60.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }

//        if(uiState.routines != null) {
//            uiState.routines?.forEach {
//                Text(text = it.name,
//                    color = MaterialTheme.colors.primaryVariant,
//                    fontSize = 60.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }


//        if (uiState.routine != null) {
//            Text(text = uiState.routine.name,
//                color = MaterialTheme.colors.primaryVariant,
//                fontSize = 40.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        if (uiState.cycles != null) {
//            uiState.cycles?.forEach {
//                Text(text = it.name,
//                    color = MaterialTheme.colors.primaryVariant,
//                    fontSize = 60.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                uiState.exercises?.get(it.id)?.forEach {
//                    Text(text = it.name,
//                        color = MaterialTheme.colors.primaryVariant,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }




//        Text(text = "BUENOS DÍAS, USUARIO",
//            color = MaterialTheme.colors.primaryVariant,
//            fontSize = 40.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Button(
//            onClick = {
////                viewModel.getRoutine(1)
//                      viewModel.getAllRoutines()
//            },
//            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
//            shape = RoundedCornerShape(50.dp)
//        ) {
//            Text(text = "Ingresar",color = MaterialTheme.colors.background)
//        }
//        if(!uiState.routines.isEmpty()) {
//            Row(
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//                RecentCard(navController, Modifier.weight(1f), uiState.currentRoutine)
//                RecentCard(navController, Modifier.weight(1f), uiState.currentRoutine)
//            }
//            for(i in uiState.routines) {
//                Row(
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                ) {
//
//                    RecentCard(navController, Modifier.weight(1f), i)
//                    RecentCard(navController, Modifier.weight(1f), i)
//                }
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




