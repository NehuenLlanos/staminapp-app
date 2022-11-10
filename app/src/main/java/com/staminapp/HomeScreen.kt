package com.staminapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.ui.theme.StaminappAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    HomeScaffold(navController)
}

@Composable
fun HomeScaffold(navController: NavController) {
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
                var selectedIndex by remember { mutableStateOf(0) }
                BottomNavigation(elevation = 10.dp) {
                    BottomNavigationItem(icon = {
                        Icon(Icons.Outlined.Home, contentDescription = "Inicio")
                    },
                        label = { Text(text = "Home") },
                        selected = (selectedIndex == 0),
                        onClick = {
                            selectedIndex = 0
                        })

                    BottomNavigationItem(icon = {
                        Icon(Icons.Outlined.Search, contentDescription = "Explorar")
                    },
                        label = { Text(text = "Explore") },
                        selected = (selectedIndex == 0),
                        onClick = {
                            selectedIndex = 0
                        })

                    BottomNavigationItem(icon = {
                        Icon(Icons.Outlined.Person, contentDescription = "Perfil")
                    },
                        label = { Text(text = "Profile") },
                        selected = (selectedIndex == 0),
                        onClick = {
                            navController.navigate(Destination.Profile.route)
                        })
                }
            }
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Text(text = "BUENOS DÍAS, USUARIO", color = MaterialTheme.colors.primaryVariant)

            for(i in 1..2) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    RecentCard(navController)
                    RecentCard(navController)
                }
            }
            Text(text = "Mi Biblioteca", color = MaterialTheme.colors.primaryVariant)
            for(i in 1..10) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RoutineCard(navController)
                    RoutineCard(navController)
                }
            }
        }
    }
}

@Composable
fun RecentCard(navController: NavController) {
    Card(
        modifier = Modifier.clickable{navController.navigate(Destination.Routine.route)},
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
    ) {
        Box(modifier = Modifier
            .height(64.dp)
            .width(160.dp),
        ) {
            Row() {
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
                    text = "Pumped Up",
                    color = MaterialTheme.colors.background,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                    )
            }
        }

    }
}

@Composable
fun RoutineCard(navController: NavController) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(160.dp)
            .clickable{navController.navigate(Destination.Routine.route)},
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StaminappAppTheme {
//        HomeScreen()
    }
}
