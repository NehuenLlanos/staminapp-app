package com.staminapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.data.model.Routine
import com.staminapp.ui.execute.ExecuteViewModel
import com.staminapp.ui.execute.Execution
import com.staminapp.ui.profile.ProfileScreen
import com.staminapp.ui.explore.ExploreScreen
import com.staminapp.ui.main.*
import com.staminapp.util.*


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    showRecent: Boolean = true,
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = getHomeViewModelFactory())
) {
    val uiState = viewModel.uiState
    if (!uiState.isFetching && uiState.routines == null && uiState.favouriteRoutines == null) {
        viewModel.getUserRoutines()
        viewModel.getFavourites()
    }

    if (uiState.message != null) {
        ApiErrorDialog {
            viewModel.getUserRoutines()
            viewModel.getFavourites()
        }
    } else if (uiState.routines == null || uiState.favouriteRoutines == null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingIndicator()
        }
    } else if (uiState.routines.isEmpty() && uiState.favouriteRoutines.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            EmptyRoutineList()
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            columns = GridCells.Adaptive(minSize = 160.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Text(
                    stringResource(R.string.good_morning).uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h2
                )
            }
            if (showRecent) {
                if (uiState.routines.isNotEmpty()) {
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
                    Text(text = stringResource(R.string.my_library),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.h3
                    )
                }
            }
            val list = mutableListOf<Routine>()
            list.addAll(uiState.routines)
            list.addAll(uiState.favouriteRoutines)
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(56.dp),
                    bitmap = decodeBase64Image(routine.image).asImageBitmap(),
                    contentDescription = stringResource(R.string.routine_image),
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




