package com.staminapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.R
import com.staminapp.ui.explore.ExploreScreen

@Composable
fun MainScaffold(
    selectedIndex: Int,
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Image(
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(1f),
                    painter = painterResource(id = R.drawable.logoblack),
                    contentDescription = "StaminApp",
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
                            if (selectedIndex != 0) {
                                navController.navigate(Destination.Home.route)
                            }
                        }
                    )

                    BottomNavigationItem(icon = {
                            if (selectedIndex == 1) {
                                Icon(Icons.Filled.Search, contentDescription = "Explorar")
                            } else {
                                Icon(Icons.Outlined.Search, contentDescription = "Explorar")
                            }
                        },
                        label = { Text(text = "Explorar") },
                        selected = (selectedIndex == 1),
                        onClick = {
                            if (selectedIndex != 1) {
                                navController.navigate(Destination.Explore.route)
                            }
                        }
                    )

                    BottomNavigationItem(icon = {
                        if (selectedIndex == 2) {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil")
                        } else {
                            Icon(Icons.Outlined.Person, contentDescription = "Perfil")
                        }
                    },
                        label = { Text(text = "Perfil") },
                        selected = (selectedIndex == 2),
                        onClick = {
                            if (selectedIndex != 2) {
                                navController.navigate(Destination.Profile.route)
                            }
                        }
                    )
                }
            }
        },
    ) {
//        HomeScreenContent(Modifier.padding(it), navController)
        ExploreScreen(Modifier.padding(it), navController = navController)
    }
}