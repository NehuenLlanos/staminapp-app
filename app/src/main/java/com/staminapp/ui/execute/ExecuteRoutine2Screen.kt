package com.staminapp.ui.execute

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.staminapp.ui.theme.Gray
import androidx.compose.material.Icon as Icon
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.staminapp.ui.main.Destination
import com.staminapp.util.decodeBase64Image
import com.staminapp.util.getExecuteViewModelFactory
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.ui.main.ApiErrorDialog
import com.staminapp.ui.main.LoadingIndicator
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ExecuteRoutine2Screen(
    routineId : Int,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel(factory = getExecuteViewModelFactory())
) {
    var selected by remember {
        mutableStateOf(false)
    }
    var index by remember {
        mutableStateOf(0)
    }
    var typeOfExecution by remember {
        mutableStateOf(-1)
    }
    var done by remember {
        mutableStateOf(false)
    }

    val uiState = viewModel.uiState

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var ticks by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            ticks++
        }
    }

    if (uiState.message == null && !uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
    }

    if (uiState.message != null) {
        ApiErrorDialog {
            viewModel.getRoutine(routineId)
        }
    } else if (uiState.routine == null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingIndicator()
        }
    } else {
        if (typeOfExecution == -1) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingIndicator()
            }

            if (uiState.cycles != null && uiState.exercises.isNotEmpty() && !uiState.isAllFetching) {
                if (uiState.cycles.size != uiState.exercises.keys.size) {
                    typeOfExecution = 1
                } else {
                    uiState.cycles.forEach { cycle ->
                        var i = 0
                        while (i < cycle.repetitions) {
                            uiState.exercises[cycle.id]!!.forEach {
                                viewModel.exercisesList.add(it)
                                viewModel.cyclesList.add(cycle)
                            }
                            i++
                        }
                    }
                    typeOfExecution = 2
                }
            } else {
                typeOfExecution = 1
            }
        }


        if (uiState.cycles != null && uiState.exercises.isNotEmpty() && !uiState.isAllFetching && typeOfExecution == 2) {
            val animDurationSec = viewModel.exercisesList[viewModel.exercisesListIndex.value].duration

            var animationPlayed by remember {
                mutableStateOf(false)
            }

            var lastTime by remember {
                mutableStateOf(0)
            }
            var animationDuration by remember {
                mutableStateOf(animDurationSec*1000)
            }

            val curTime = animateIntAsState(
                targetValue =  if (animationPlayed) animDurationSec else lastTime,
                animationSpec = tween (
                    durationMillis = if (animationPlayed) animationDuration else 0,
                    delayMillis = 0,
                    easing = LinearEasing
                )
            )

            LaunchedEffect(key1 = true) {
                animationPlayed = true
            }
            Scaffold { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ){
                        Image(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp),
                            bitmap = decodeBase64Image(uiState.routine.image).asImageBitmap(),
                            contentDescription = stringResource(R.string.routine_image),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = uiState.routine.name.uppercase(),
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.h1,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(viewModel.exercisesList.size) { i ->
                            ExecutionExercise(
                                idx = i,
                                title = viewModel.exercisesList[i].name,
                                cycle = viewModel.cyclesList[i].name,
                                selected = i == viewModel.exercisesListIndex.value,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0x11000000)
                                    )
                                )
                            )
                    )
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .background(White)
                            .padding(16.dp)
                    ) {
                        if (viewModel.exercisesList[viewModel.exercisesListIndex.value].duration > 0) {
                            val sb = java.lang.StringBuilder()

                            val seconds = TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong())
                            Text(
                                text = sb
                                    .append(
                                        TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value)
                                            .toLong())
                                            .toString()
                                    )
                                    .append(":")
                                    .append(
                                        if (seconds < 10) {
                                            "0"
                                        } else {
                                            ""
                                        }
                                    )
                                    .append(seconds.toString())
                                    .toString(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primaryVariant,
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (viewModel.exercisesList[viewModel.exercisesListIndex.value].repetitions > 0) {
                            Text(
                                text = "x" + viewModel.exercisesList[viewModel.exercisesListIndex.value].repetitions.toString(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primaryVariant,
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                            shape = RoundedCornerShape(16.dp),
                            enabled = viewModel.exercisesList[viewModel.exercisesListIndex.value].duration > 0,
                            onClick = {
                                if (animationPlayed) {
                                    lastTime = curTime.value
                                    animationPlayed = false
                                } else {
                                    animationDuration = (animDurationSec - lastTime)*1000
                                    animationPlayed = true
                                }
                            }
                        ) {
                            if (animationPlayed) {
                                Icon(
                                    Icons.Filled.Pause,
                                    contentDescription = null,
                                    tint = White,
                                )
                            } else {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    tint = White,
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            /* BOTON VOLVER PARA EL EJERCICIO ANTERIOR */
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD28C)),
                                shape = RoundedCornerShape(16.dp),
                                enabled = viewModel.exercisesList.isNotEmpty() && viewModel.exercisesListIndex.value > 0,
                                onClick = {
                                    viewModel.setExercisesListIndex(viewModel.exercisesListIndex.value - 1)
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(index = viewModel.exercisesListIndex.value, -5)
                                    }
                                    typeOfExecution = 3
                                }
                            ) {
                                Icon(
                                    Icons.Filled.FastRewind,
                                    contentDescription = null,
                                    tint = White,
                                )
                            }
                            /* BOTON IR AL SIGUIENTE EJERCICIO */
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD28C)),
                                shape = RoundedCornerShape(16.dp),
                                onClick = {
                                    if (viewModel.exercisesList.isNotEmpty() && viewModel.exercisesListIndex.value < viewModel.exercisesList.size - 1) {
                                        viewModel.setExercisesListIndex(viewModel.exercisesListIndex.value + 1)
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(
                                                index = viewModel.exercisesListIndex.value,
                                                -5
                                            )
                                        }
                                        typeOfExecution = 3
                                    } else {
                                        navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.FastForward,
                                    contentDescription = null,
                                    tint = White,
                                )
                            }
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                            }
                        ) {
                            Text(text = stringResource(R.string.end_routine_execution).uppercase(),
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.button,
                            )
                        }
                    }

                }
            }

        }
        if (typeOfExecution == 1) {
            typeOfExecution = -1
        }
        else if (typeOfExecution == 3) {
            typeOfExecution = 2
        }
    }
}

@Composable
fun ExecutionExercise(
    modifier: Modifier = Modifier,
    idx: Int,
    title: String,
    cycle: String,
    selected: Boolean = false,
) {
    val color: Color = when {
        selected -> White
        else -> Color(0xFF999FA7)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .background(
                when {
                    selected -> MaterialTheme.colors.primaryVariant
                    else -> Gray
                }
            )
            .padding(start = 12.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "#$idx",
                style = MaterialTheme.typography.h1,
                color = color,
            )
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.subtitle1,
                    color = color,
                    modifier = Modifier
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.FitnessCenter,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(cycle,
                        style = MaterialTheme.typography.body1,
                        color = color,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}