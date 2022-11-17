package com.staminapp.ui.routines

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.staminapp.ui.theme.Gray
import androidx.compose.material.Icon as Icon
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.ui.main.Destination
import com.staminapp.data.model.Exercise
import com.staminapp.ui.main.CustomChip
import com.staminapp.ui.main.LoadingIndicator
import com.staminapp.ui.main.RatingBar
import com.staminapp.util.decodeBase64Image
import com.staminapp.util.getRoutineViewModelFactory
import com.staminapp.util.translateDifficultyForApp

@Composable
fun RoutineScreen(
    routineId: Int,
    isAuthenticated: Boolean = true,
    navController: NavController,
    viewModel: RoutineViewModel = viewModel(factory = getRoutineViewModelFactory())
) {
    val uiState = viewModel.uiState

    if (!uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
        viewModel.isFavourite()
        viewModel.getCyclesForRoutine(routineId)
    }

    if (uiState.exercises.isEmpty() && uiState.cycles != null) {
        uiState.cycles.forEach {
            viewModel.getExercisesForCycle(it.id)
        }
    }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, stringResource(R.string.share_text) + " https://www.staminapp.com/routine/" + routineId.toString())
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, stringResource(R.string.share_title))
    val context = LocalContext.current

    var isDescriptionOpen by remember { mutableStateOf(true) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.routine))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (uiState.isFavourite!!) {
                            viewModel.deleteFavourite(routineId)
                        } else {
                            viewModel.putFavourite(routineId)
                        }
                    }) {
                        when {
                            uiState.isFavourite!! -> Icon(Icons.Filled.Favorite, contentDescription = stringResource(R.string.unmark_as_favourite), tint = White)
                            else -> Icon(Icons.Filled.FavoriteBorder, contentDescription = stringResource(R.string.mark_as_favourite), tint = White)
                        }

                    }
                    IconButton(onClick = {
                        context.startActivity(shareIntent)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = stringResource(R.string.share), tint = White)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton (
                onClick = {
                    // TODO
                    navController.navigate(Destination.ExecuteRoutine.route)
                },
                icon = {
                    Icon(Icons.Filled.FitnessCenter, contentDescription = null)
                },
                text = {
                    Text(stringResource(R.string.start).uppercase())
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        if (!isAuthenticated) {
            AlertDialog(
                onDismissRequest = {},
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Destination.SignIn.route)
                            }
                        ) {
                            Text(stringResource(R.string.login))
                        }
                    }
                },
                title = {
                    Text(stringResource(R.string.error))
                },
                text = {
                    Text(stringResource(R.string.not_logged_in_message))
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (uiState.routine == null) {
                    item {
                        LoadingIndicator()
                    }
                } else {
                    item {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                bitmap = decodeBase64Image(uiState.routine.image).asImageBitmap(),
                                contentDescription = stringResource(R.string.routine_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .aspectRatio(1f / 1f)
                                    .clip(RoundedCornerShape(10))
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                CustomChip(
                                    selected = false,
                                    text = translateDifficultyForApp(uiState.routine.difficulty)
                                )
                                Text(
                                    uiState.routine.name.uppercase(),
                                    style = MaterialTheme.typography.h2,
                                    lineHeight = 42.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.rating),
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.primaryVariant
                            )
                            RatingBar(
                                rating = uiState.routine.score,
                                starsColor = MaterialTheme.colors.primary,
                                modifier = Modifier.padding(bottom = 2.dp),
                                rate = {
                                    viewModel.rate(routineId, it)
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isDescriptionOpen = !isDescriptionOpen
                                }
                        ) {
                            Text(stringResource(R.string.description),
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.primaryVariant
                            )
                            Icon(
                                when {
                                    isDescriptionOpen -> Icons.Filled.ExpandLess
                                    else -> Icons.Filled.ExpandMore
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                        if (isDescriptionOpen) {
                            Text(
                                uiState.routine.detail,
                                color = MaterialTheme.colors.primaryVariant,
                                style = MaterialTheme.typography.body1
                            )

                        }
                        Spacer(modifier = Modifier
                            .padding(top = 8.dp)
                            .height(4.dp)
                            .fillMaxWidth()
                            .background(Gray))
                        Text(stringResource(R.string.cycles),
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(uiState.cycles!!) {
                        Cycle(
                            title = it.name,
                            repetitions = it.repetitions,
                            exercises = uiState.exercises.getOrDefault(it.id, listOf()),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun Cycle(
    modifier: Modifier = Modifier,
    title: String,
    repetitions: Int,
    exercises: List<Exercise>
) {
    var isOpen by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .border(1.5.dp, MaterialTheme.colors.primaryVariant, RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpen = !isOpen
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(title.uppercase(),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primaryVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "x$repetitions",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(6.dp, 4.dp)
                )
            }
            Icon(
                when {
                    isOpen -> Icons.Filled.ExpandLess
                    else -> Icons.Filled.ExpandMore
                },
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        if (isOpen) {
            Column {
                exercises.forEach { exercise ->
                    var info = ""
                    if (exercise.duration > 0 ) {
                        info += exercise.duration.toString() + stringResource(R.string.second) + if (exercise.duration == 1) "" else "s"
                    }
                    if (info != "") {
                        info += " | "
                    }
                    if (exercise.repetitions > 0 ) {
                        info += "${exercise.repetitions} ${if (exercise.repetitions == 1) stringResource(R.string.repetition) else stringResource(R.string.repetitions)}"
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            exercise.name,
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            info,
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}