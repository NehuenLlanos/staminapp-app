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
import com.staminapp.data.model.Exercise
import com.staminapp.data.model.Routine
import com.staminapp.ui.main.*
import com.staminapp.util.*

@Composable
fun RoutineScreen(
    routineId: Int,
    isAuthenticated: Boolean = true,
    dropdownsOpen: Boolean = true,
    navController: NavController,
    viewModel: RoutineViewModel = viewModel(factory = getRoutineViewModelFactory())
) {
    val uiState = viewModel.uiState

    if (uiState.message == null && !uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
        viewModel.isFavourite()
        viewModel.getCyclesForRoutine(routineId)
    }

    if (uiState.message == null && uiState.exercises.isEmpty() && uiState.cycles != null) {
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

    val windowInfo = rememberWindowInfo()

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
                    navController.navigate(Destination.ExecutionPreviewRoutine.createRoute(routineId))
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
                                navController.navigate(
                                    Destination.SignIn.withAfter(
                                        Destination.Routine.createRoute(
                                            routineId
                                        )
                                    )
                                )
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
        } else if (uiState.message != null) {
            ApiErrorDialog {
                viewModel.getRoutine(routineId)
                viewModel.isFavourite()
                viewModel.getCyclesForRoutine(routineId)
            }
        } else {
            if (uiState.routine == null) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoadingIndicator()
                }
            } else {
                if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Compact && windowInfo.orientation is WindowInfo.Orientation.Landscape) {
                    Row(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.4f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            RoutineInfo(
                                routine = uiState.routine,
                                dropdownsOpen = dropdownsOpen,
                                viewModel = viewModel
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.weight(0.6f)
                        ) {
                            item {
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
                                    dropdownsOpen = dropdownsOpen,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        item {
                            RoutineInfo(
                                routine = uiState.routine,
                                dropdownsOpen = dropdownsOpen,
                                viewModel = viewModel
                            )
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
                                dropdownsOpen = dropdownsOpen,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineInfo(
    routine: Routine,
    dropdownsOpen: Boolean,
    viewModel: RoutineViewModel
) {
    var isDescriptionOpen by remember { mutableStateOf(dropdownsOpen) }
    val windowInfo = rememberWindowInfo()

    var maxWidth = 0.4f
    if (windowInfo.orientation is WindowInfo.Orientation.Portrait && windowInfo.screenWidthInfo !is WindowInfo.WindowType.Compact) {
        maxWidth = 0.2f
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            bitmap = decodeBase64Image(routine.image).asImageBitmap(),
            contentDescription = stringResource(R.string.routine_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(maxWidth)
                .aspectRatio(1f / 1f)
                .clip(RoundedCornerShape(10))
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CustomChip(
                    selected = false,
                    text = translateDifficultyForApp(routine.difficulty)
                )
            }
            Text(
                routine.name.uppercase(),
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
            rating = routine.score,
            starsColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(bottom = 2.dp),
            rate = {
                viewModel.rate(routine.id, it)
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
            routine.detail,
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body1
        )

    }
}

@Composable
fun Cycle(
    modifier: Modifier = Modifier,
    title: String,
    repetitions: Int,
    exercises: List<Exercise>,
    dropdownsOpen: Boolean
) {
    var isOpen by remember { mutableStateOf(dropdownsOpen) }

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
                        info += exercise.duration.toString() + " " + stringResource(R.string.second) + if (exercise.duration == 1) "" else "s"
                    }
                    if (info != "" && exercise.repetitions > 0 ) {
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