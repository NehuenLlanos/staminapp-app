package com.staminapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.Destination
import com.staminapp.R
import com.staminapp.data.model.Routine
import com.staminapp.ui.main.RoutineCard
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
                        }
                    )

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
                        }
                    )

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
                        }
                    )
                }
            }
        },
    ) {
        HomeScreenContent(Modifier.padding(it), navController)
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
    viewModel: HomeViewModel = viewModel(factory = getHomeViewModelFactory())
) {
    val uiState = viewModel.uiState
    if (!uiState.isFetching && uiState.routines == null && uiState.favouriteRoutines == null) {
        viewModel.getUserRoutines()
        viewModel.getFavourites()
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.routines == null && uiState.favouriteRoutines == null) {
            item {

            }
        } else {
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Text(
                    "Buenos días".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h2
                )
            }
            if(uiState.routines != null && uiState.routines.isNotEmpty()) {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RecentCard(
                            navController,
                            Modifier.weight(1f),
                            uiState.routines.last()
                        )
                        if (uiState.routines.size >= 2) {
                            RecentCard(
                                navController,
                                Modifier.weight(1f),
                                uiState.routines[uiState.routines.lastIndex - 1]
                            )
                        }
                    }
                }
            }
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Text(text = "Mi Biblioteca",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h3
                )
            }
            val list = mutableListOf<Routine>()
            if (uiState.routines != null) {
                list.addAll(uiState.routines)
            }
            if (uiState.favouriteRoutines != null) {
                list.addAll(uiState.favouriteRoutines)
            }
            items(list) {
                RoutineCard(navController = navController, it)
            }
        }
    }
}

@Composable
fun RecentCard(navController: NavController, modifier: Modifier = Modifier, routine: Routine) {
    Card(
        modifier = modifier.clickable{
            navController.navigate(Destination.Routine.createRoute(routine.id))
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(56.dp),
                    bitmap = decodeBase64Image(routine.image).asImageBitmap(),
                    contentDescription = "Imagen de Rutina",
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




