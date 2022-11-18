package com.staminapp.ui.execute

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.staminapp.R
import com.staminapp.ui.main.ApiErrorDialog
import com.staminapp.ui.main.Destination
import com.staminapp.ui.main.LoadingIndicator
import com.staminapp.ui.main.RatingBar
import com.staminapp.ui.theme.Gray
import com.staminapp.util.decodeBase64Image
import com.staminapp.util.getExecuteViewModelFactory
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Execution(
    routineId : Int,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel(factory = getExecuteViewModelFactory())
) {
    var typeOfExecution by remember {
        mutableStateOf(-1)
    }
    val uiState = viewModel.uiState

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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingIndicator()
        }

        if (typeOfExecution == -1) {
            if (uiState.cycles != null && uiState.exercises.isNotEmpty() && !uiState.isAllFetching) {

                if (uiState.cycles.size != uiState.exercises.keys.size) {
                    typeOfExecution = 0
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
                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)

                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                        typeOfExecution = 1 // Time and Reps
                    } else if (exercise.duration == 0) {
                        typeOfExecution = 2 // Reps
                    } else {
                        typeOfExecution = 3 // Time
                    }
                }
            } else {
                typeOfExecution = 0
            }
        }
        else if (typeOfExecution == 1 && uiState.cycles != null && uiState.exercises.isNotEmpty()) { // Time and Reps

            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val fontSize = 20.sp
                val radius = 50.dp
                val color = MaterialTheme.colors.primary
                val stokeWidth = 12.dp
                val animDurationSec = viewModel.exercisesList.get(viewModel.exercisesListIndex.value).duration

                var animationPlayed by remember {
                    mutableStateOf(false)
                }
                var lastPercentage by remember {
                    mutableStateOf(1f)
                }
                var lastTime by remember {
                    mutableStateOf(0)
                }
                var animationDuration by remember {
                    mutableStateOf(animDurationSec*1000)
                }

                val curPercentage = animateFloatAsState(
                    targetValue = if (animationPlayed) 0f else lastPercentage,
                    animationSpec = tween (
                        durationMillis = if (animationPlayed) animationDuration else 0,
                        delayMillis = 0,
                        easing = LinearEasing
                    )
                )
                val curTime = animateIntAsState(
                    targetValue =  if (animationPlayed) animDurationSec else lastTime,
                    animationSpec = tween (
                        durationMillis = if (animationPlayed) animationDuration else 0,
                        delayMillis = 0,
                        easing = LinearEasing
                    )
                )

                uiState.routine.image?.let {
                    TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),it, uiState.routine.name, viewModel.progressBar.value)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExerciseHeader(
                        viewModel.cyclesList.get(viewModel.exercisesListIndex.value).name,
                        viewModel.exercisesList.get(viewModel.exercisesListIndex.value).name
                    )
                    Column(
                        modifier = Modifier
                            .height(264.dp)
                            .width(264.dp)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(Gray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        val sb = java.lang.StringBuilder()
                        LaunchedEffect(key1 = true) {
                            animationPlayed = true
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(radius * 2f)
                        ) {
                            Canvas(modifier = Modifier.size(radius * 2f)) {
                                drawArc(
                                    color = color,
                                    -90f,
                                    360 *  curPercentage.value,
                                    useCenter = false,
                                    style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            val seconds = TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong())
                            Text(
                                text = sb.append(TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value).toLong()).toString())
                                    .append(":")
                                    .append(
                                        if (seconds < 10) {
                                            "0"
                                        } else {
                                            ""
                                        }
                                    )
                                    .append(seconds.toString()).toString(),
                                color = MaterialTheme.colors.primary,
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "x" + viewModel.exercisesList.get(viewModel.exercisesListIndex.value).repetitions.toString(),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 64.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            if (animationPlayed) {
                                lastTime = curTime.value
                                lastPercentage = curPercentage.value
                                animationPlayed = false
                            } else {
                                animationDuration = (animDurationSec - lastTime)*1000
                                animationPlayed = true
                            }
                        }) {
                        if (animationPlayed) {
                            Icon(
                                Icons.Filled.Pause,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        } else {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
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
                            onClick = {
                                if (viewModel.exercisesList.isNotEmpty() && viewModel.exercisesListIndex.value > 0) {
                                    viewModel.setExercisesListIndex(viewModel.exercisesListIndex.value - 1)
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarDec(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 4 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 2 // Reps
                                    } else {
                                        typeOfExecution = 3 // Time
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastRewind,
                                contentDescription = null,
                                tint = Color.White,
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
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarInc(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 4 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 2 // Reps
                                    } else {
                                        typeOfExecution = 3 // Time
                                    }
                                } else {
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastForward,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                        }) {
                        Text(
                            text = stringResource(R.string.end_routine_execution).uppercase(),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
            }
        } else if (typeOfExecution == 2 && uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty() ) { // Reps

            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                uiState.routine.image?.let {
                    TopBarRoutineExecution(Modifier.padding(bottom = 16.dp), it, uiState.routine.name, viewModel.progressBar.value)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExerciseHeader(
                        viewModel.cyclesList.get(viewModel.exercisesListIndex.value).name,
                        viewModel.exercisesList.get(viewModel.exercisesListIndex.value).name
                    )
                    Column(
                        modifier = Modifier
                            .height(264.dp)
                            .width(264.dp)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(Gray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = viewModel.exercisesList.get(viewModel.exercisesListIndex.value).repetitions.toString(),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h1,
                            fontSize = 56.sp,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(text = stringResource(R.string.repetitions).uppercase(),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 64.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                            onClick = {
                                if (viewModel.exercisesList.isNotEmpty() && viewModel.exercisesListIndex.value > 0) {
                                    viewModel.setExercisesListIndex(viewModel.exercisesListIndex.value - 1)
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarDec(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 1 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 5 // Reps
                                    } else {
                                        typeOfExecution = 3 // Time
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastRewind,
                                contentDescription = null,
                                tint = Color.White,
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
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarInc(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 1 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 5 // Reps
                                    } else {
                                        typeOfExecution = 3 // Time
                                    }
                                } else {
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastForward,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                        }) {
                        Text(
                            text = stringResource(R.string.end_routine_execution).uppercase(),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
            }
        } else if (typeOfExecution == 3 && uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty()) { // Time

            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val fontSize = 40.sp
                val radius = 80.dp
                val color = MaterialTheme.colors.primary
                val stokeWidth = 16.dp
                val animDurationSec = viewModel.exercisesList.get(viewModel.exercisesListIndex.value).duration

                var animationPlayed by remember {
                    mutableStateOf(false)
                }
                var lastPercentage by remember {
                    mutableStateOf(1f)
                }
                var lastTime by remember {
                    mutableStateOf(0)
                }
                var animationDuration by remember {
                    mutableStateOf(animDurationSec*1000)
                }

                val curPercentage = animateFloatAsState(
                    targetValue = if (animationPlayed) 0f else lastPercentage,
                    animationSpec = tween (
                        durationMillis = if (animationPlayed) animationDuration else 0,
                        delayMillis = 0,
                        easing = LinearEasing
                    )
                )
                val curTime = animateIntAsState(
                    targetValue =  if (animationPlayed) animDurationSec else lastTime,
                    animationSpec = tween (
                        durationMillis = if (animationPlayed) animationDuration else 0,
                        delayMillis = 0,
                        easing = LinearEasing
                    )
                )
                uiState.routine.image?.let {
                    TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),
                        it, uiState.routine.name, viewModel.progressBar.value)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExerciseHeader(
                        viewModel.cyclesList.get(viewModel.exercisesListIndex.value).name,
                        viewModel.exercisesList.get(viewModel.exercisesListIndex.value).name
                    )
                    Column(
                        modifier = Modifier
                            .height(264.dp)
                            .width(264.dp)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(Gray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        val sb = java.lang.StringBuilder()

                        LaunchedEffect(key1 = true) {
                            animationPlayed = true
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(radius * 2f)
                        ) {
                            Canvas(modifier = Modifier.size(radius * 2f)) {
                                drawArc(
                                    color = color,
                                    -90f,
                                    360 *  curPercentage.value,
                                    useCenter = false,
                                    style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            val seconds = TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong())
                            Text(
                                text = sb.append(TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value).toLong()).toString())
                                    .append(":")
                                    .append(
                                        if (seconds < 10) {
                                            "0"
                                        } else {
                                            ""
                                        }
                                    )
                                    .append(seconds.toString()).toString(),
                                color = MaterialTheme.colors.primary,
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 64.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            if (animationPlayed) {
                                lastTime = curTime.value
                                lastPercentage = curPercentage.value
                                animationPlayed = false
                            } else {
                                animationDuration = (animDurationSec - lastTime)*1000
                                animationPlayed = true
                            }
                        }) {
                        if (animationPlayed) {
                            Icon(
                                Icons.Filled.Pause,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        } else {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
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
                            onClick = {
                                if (viewModel.exercisesList.isNotEmpty() && viewModel.exercisesListIndex.value > 0) {
                                    viewModel.setExercisesListIndex(viewModel.exercisesListIndex.value - 1)
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarDec(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 1 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 2 // Reps
                                    } else {
                                        typeOfExecution = 6 // Time
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastRewind,
                                contentDescription = null,
                                tint = Color.White,
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
                                    val exercise = viewModel.exercisesList.get(viewModel.exercisesListIndex.value)
                                    viewModel.setProgressBarInc(1/viewModel.exercisesList.size.toFloat())
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 1 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 2 // Reps
                                    } else {
                                        typeOfExecution = 6 // Time
                                    }
                                } else {
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.FastForward,
                                contentDescription = null,
                                tint = Color.White,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                        }) {
                        Text(
                            text = stringResource(R.string.end_routine_execution).uppercase(),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
            }
        } else if (typeOfExecution == 0) {
            typeOfExecution = -1
        } else if (typeOfExecution == 4) {
            typeOfExecution = 1
        } else if (typeOfExecution == 5) {
            typeOfExecution = 2
        } else if(typeOfExecution == 6) {
            typeOfExecution = 3
        }
    }
}

@Composable
fun TopBarRoutineExecution(
    modifier: Modifier,
    image : String,
    routineName : String,
    progress : Float
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                bitmap = decodeBase64Image(image).asImageBitmap(),
                contentDescription = stringResource(R.string.routine_image),
                contentScale = ContentScale.Crop
            )
            Text(text = routineName.uppercase(),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ExerciseHeader(cycle : String, exercise : String) {
    Text(text = cycle.uppercase(),
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    Text(text = exercise,
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}