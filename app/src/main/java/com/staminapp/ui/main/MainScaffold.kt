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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.R
import com.staminapp.ui.explore.ExploreScreen

@Composable
fun MainScaffold(
    selectedIndex: Int,
    navController: NavController,
    content: @Composable (modifier: Modifier, navController: NavController) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Image(
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(1f),
                    painter = painterResource(id = R.drawable.logoblack),
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigation(elevation = 10.dp) {
                    BottomNavigationItem(icon = {
                            if (selectedIndex == 0) {
                                Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.home_bottom_bar))
                            } else {
                                Icon(Icons.Outlined.Home, contentDescription = stringResource(R.string.home_bottom_bar))
                            }
                        },
                        label = { Text(text = stringResource(R.string.home_bottom_bar)) },
                        selected = (selectedIndex == 0),
                        onClick = {
                            if (selectedIndex != 0) {
                                navController.navigate(Destination.Home.route)
                            }
                        }
                    )

                    BottomNavigationItem(icon = {
                            if (selectedIndex == 1) {
                                Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.explore_bottom_bar))
                            } else {
                                Icon(Icons.Outlined.Search, contentDescription = stringResource(R.string.explore_bottom_bar))
                            }
                        },
                        label = { Text(text = stringResource(R.string.explore_bottom_bar)) },
                        selected = (selectedIndex == 1),
                        onClick = {
                            if (selectedIndex != 1) {
                                navController.navigate(Destination.Explore.route)
                            }
                        }
                    )

                    BottomNavigationItem(icon = {
                        if (selectedIndex == 2) {
                            Icon(Icons.Filled.Person, contentDescription = stringResource(R.string.profile_bottom_bar))
                        } else {
                            Icon(Icons.Outlined.Person, contentDescription = stringResource(R.string.profile_bottom_bar))
                        }
                    },
                        label = { Text(text = stringResource(R.string.profile_bottom_bar)) },
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
        content(Modifier.padding(it), navController)
    }
}