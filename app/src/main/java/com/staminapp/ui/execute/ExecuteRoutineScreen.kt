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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.staminapp.ui.main.Destination
import com.staminapp.ui.main.RatingBar
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


    if (!uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
//        viewModel.getCyclesForRoutine(routineId)
    }

//    if (uiState.exercises.isEmpty() && uiState.cycles != null) {
//        uiState.cycles.forEach {
//            viewModel.getExercisesForCycle(it.id)
//        }
//    }


    if (typeOfExecution == -1) {
        if (uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty() && !uiState.isAllFetching) {

                val cycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                val exercise = viewModel.uiState.exercises!!.get(cycle.id)!!.get(viewModel.currentExerciseIndex.value)



                if (exercise.duration != 0 && exercise.repetitions != 0) {
                    typeOfExecution = 0 // Time and Reps
                } else if (exercise.duration == 0) {
                    typeOfExecution = 1 // Reps
                } else {
                    typeOfExecution = 2 // Time
                }



        }
    }
    else if (typeOfExecution == 0 && uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty()) { // Time and Reps
        var totalDivisions = 0
        uiState.cycles.forEach {
            totalDivisions += it.repetitions * uiState.exercises!!.get(it.id)!!.size
        }
        viewModel.setProgressBarInc(1/totalDivisions.toFloat())

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
            var animDurationSec = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!
                .get(viewModel.currentExerciseIndex.value).duration

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

            uiState.routine!!.image?.let {
                TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),it, uiState.routine.name, viewModel.progressBar.value)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExerciseHeader(
                    viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).name,
                    viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value).name
                )
                Column(
                    modifier = Modifier
                        .height(264.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
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
                        Text(
                            text = sb.append(TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value).toLong()).toString())
                                .append(":")
                                .append(TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong()).toString()).toString(),
                            color = MaterialTheme.colors.primary,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(text = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value).repetitions.toString(),
                        color = MaterialTheme.colors.primary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(text = "Repeticiones".uppercase(),
                        color = MaterialTheme.colors.primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
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
                    Icon(Icons.Filled.Pause, contentDescription = "Inicio")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        /* 1. Incrementar el indice indexExercises */
                        viewModel.setExerciseIndex(viewModel.currentExerciseIndex.value + 1)

                        viewModel.setProgressBar()

                        val currentCycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                        val exerciseQuantity = viewModel.uiState.exercises!!.get(currentCycle.id)!!.size

                        println(viewModel.currentExerciseIndex.value.toString())

                        /* Si la cantidad de ejercicios del ciclo actual es igual al indexExercise */
                        if (viewModel.currentExerciseIndex.value == exerciseQuantity) {
                            /* Incrmento las repeticiones del ciclo actual */
                            viewModel.setCycleReps(viewModel.currentCycleReps.value + 1)

                            /* Si la cantidad de repeticiones del ciclo actual es igual a la cantidad de currentCycleReps */
                            if (viewModel.currentCycleReps.value == currentCycle.repetitions) {


                                /* Termino mi ciclo e incremento el currentCycleIndex para obtener el siguiente ciclo */
                                viewModel.setCycleIndex(viewModel.currentCycleIndex.value + 1)
                                /* Verifico si llegue al final de mi rutina comparando el currentCycleIndex con la cantidad total de ciclos en el vector de ciclos*/
                                if (viewModel.currentCycleIndex.value == viewModel.uiState.cycles!!.size) {
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                    typeOfExecution = 9
                                }
                                else {
                                    viewModel.setExerciseIndex(0)
                                    val cycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                                    val exercise = viewModel.uiState.exercises!!.get(cycle.id)!!.get(viewModel.currentExerciseIndex.value)
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 3 // Time and Reps

                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 1 // Reps
                                    } else {
                                        typeOfExecution = 2 // Time
                                    }
                                }
                            }
                            else {
                                viewModel.setExerciseIndex(0)
                                println("ACA ESTOY POR REPETIR EL CICLO 2 ")
                                println("IndexCiclo " + viewModel.currentCycleIndex.value.toString())
                                println("IndexEjercicio " + viewModel.currentExerciseIndex.value.toString())

                                val exercise = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value)
                                println(exercise.name)
                                if (exercise.duration != 0 && exercise.repetitions != 0) {
                                    typeOfExecution = 3 // Time and Reps
                                } else if (exercise.duration == 0) {
                                    typeOfExecution = 1 // Reps
                                } else {
                                    typeOfExecution = 2 // Time
                                }
                            }
                        }

                        else { // Cambio al siguiente ejercicio
                            val exercise = viewModel.uiState.exercises!!.get(
                                viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id
                            )!!.get(viewModel.currentExerciseIndex.value)
                            println(exercise.name)
                            if (exercise.duration != 0 && exercise.repetitions != 0) {
                                typeOfExecution = 3 // Time and Reps
                            } else if (exercise.duration == 0) {
                                typeOfExecution = 1 // Reps
                            } else {
                                typeOfExecution = 2 // Time
                            }
                        }


                    }) {
                    Text(
                        text = "Finalizar Ejercicio".uppercase(),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.body2,
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                    }) {
                    Text(
                        text = "Finalizar Rutina".uppercase(),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
    else if (typeOfExecution == 1 && uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty() ) { // Reps
        var totalDivisions = 0
        uiState.cycles.forEach {
            totalDivisions += it.repetitions * uiState.exercises!!.get(it.id)!!.size
        }
        viewModel.setProgressBarInc(1/totalDivisions.toFloat())

        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.routine.image?.let {
                TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),
                    it, uiState.routine.name, viewModel.progressBar.value)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExerciseHeader(
                    viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).name,
                    viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value).name
                )
                Column(
                    modifier = Modifier
                        .height(264.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value).repetitions.toString(),
                        color = MaterialTheme.colors.primary,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(text = "Repeticiones".uppercase(),
                        color = MaterialTheme.colors.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
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
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        /* 1. Incrementar el indice indexExercises */
                        viewModel.setExerciseIndex(viewModel.currentExerciseIndex.value + 1)

                        viewModel.setProgressBar()

                        val currentCycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                        val exerciseQuantity = viewModel.uiState.exercises!!.get(currentCycle.id)!!.size

                        println(viewModel.currentExerciseIndex.value.toString())

                        /* Si la cantidad de ejercicios del ciclo actual es igual al indexExercise */
                        if (viewModel.currentExerciseIndex.value == exerciseQuantity) {
                            /* Incrmento las repeticiones del ciclo actual */
                            viewModel.setCycleReps(viewModel.currentCycleReps.value + 1)

                            /* Si la cantidad de repeticiones del ciclo actual es igual a la cantidad de currentCycleReps */
                            if (viewModel.currentCycleReps.value == currentCycle.repetitions) {


                                /* Termino mi ciclo e incremento el currentCycleIndex para obtener el siguiente ciclo */
                                viewModel.setCycleIndex(viewModel.currentCycleIndex.value + 1)
                                /* Verifico si llegue al final de mi rutina comparando el currentCycleIndex con la cantidad total de ciclos en el vector de ciclos*/
                                if (viewModel.currentCycleIndex.value == viewModel.uiState.cycles!!.size) {
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                    typeOfExecution = 9
                                }
                                else {
                                    viewModel.setExerciseIndex(0)
                                    val cycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                                    val exercise = viewModel.uiState.exercises!!.get(cycle.id)!!.get(viewModel.currentExerciseIndex.value)
                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 0 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 4 // Reps
                                    } else {
                                        typeOfExecution = 2 // Time
                                    }
                                }
                            }
                            else {
                                viewModel.setExerciseIndex(0)
                                println("ACA ESTOY POR REPETIR EL CICLO 2 ")
                                println("IndexCiclo " + viewModel.currentCycleIndex.value.toString())
                                println("IndexEjercicio " + viewModel.currentExerciseIndex.value.toString())

                                val exercise = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value)
                                println(exercise.name)
                                if (exercise.duration != 0 && exercise.repetitions != 0) {
                                    typeOfExecution = 0 // Time and Reps
                                } else if (exercise.duration == 0) {
                                    typeOfExecution = 4 // Reps
                                } else {
                                    typeOfExecution = 2 // Time
                                }
                            }
                        }

                        else { // Cambio al siguiente ejercicio
                            val exercise = viewModel.uiState.exercises!!.get(
                                viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id
                            )!!.get(viewModel.currentExerciseIndex.value)
                            println(exercise.name)
                            if (exercise.duration != 0 && exercise.repetitions != 0) {
                                typeOfExecution = 0 // Time and Reps
                            } else if (exercise.duration == 0) {
                                typeOfExecution = 4 // Reps
                            } else {
                                typeOfExecution = 2 // Time
                            }
                        }
                    }) {
                    Text(
                        text = "Listo",
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.body2,
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                    }) {
                    Text(
                        text = "Finalizar Rutina".uppercase(),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
    else if (typeOfExecution == 2 && uiState.routine != null && uiState.cycles != null && uiState.exercises.isNotEmpty()) { // Time
        var totalDivisions = 0
        uiState.cycles.forEach {
            totalDivisions += it.repetitions * uiState.exercises!!.get(it.id)!!.size
        }
        viewModel.setProgressBarInc(1/totalDivisions.toFloat())

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
            var animDurationSec = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!
                .get(viewModel.currentExerciseIndex.value).duration

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
            uiState.routine!!.image?.let {
                TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),
                    it, uiState.routine.name, viewModel.progressBar.value)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExerciseHeader(
                    viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).name,
                    viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value).name
                )
                Column(
                    modifier = Modifier
                        .height(264.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
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
                        Text(
                            text = sb.append(TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value).toLong()).toString())
                                .append(":")
                                .append(TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong()).toString()).toString(),
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
                    Icon(Icons.Filled.Pause, contentDescription = "Inicio")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        /* 1. Incrementar el indice indexExercises */
                        viewModel.setExerciseIndex(viewModel.currentExerciseIndex.value + 1)

                        viewModel.setProgressBar()

                        val currentCycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                        val exerciseQuantity = viewModel.uiState.exercises!!.get(currentCycle.id)!!.size

                        println(viewModel.currentExerciseIndex.value.toString())

                        /* Si la cantidad de ejercicios del ciclo actual es igual al indexExercise */
                        if (viewModel.currentExerciseIndex.value >= exerciseQuantity) {
                            /* Incrmento las repeticiones del ciclo actual */
                            viewModel.setCycleReps(viewModel.currentCycleReps.value + 1)

                            println("INDEX DEL CICLO DEL ORTO " + viewModel.currentCycleIndex.value.toString())

                            println("CANTIDAD DE ELEMENTOS EN LA LISTA DE CICLOS DE MIERDA " + viewModel.uiState.cycles!!.size.toString())

                            println("CANTIDAD DE CICLOS DE MIERDA QUE TENGO HASTA AHORA " + viewModel.currentCycleReps.value.toString())

                            println("CANTIDAD DE CICLOS DE MIERDA QUE TENGO QUE HACER " + viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).repetitions.toString())

                            /* Si la cantidad de repeticiones del ciclo actual es igual a la cantidad de currentCycleReps */
                            if (viewModel.currentCycleReps.value >= currentCycle.repetitions) {
                                println("ESTOY CUANDO HAY QUE VER SI LLEGUE AL FINAL O NO")


                                /* Termino mi ciclo e incremento el currentCycleIndex para obtener el siguiente ciclo */
                                viewModel.setCycleIndex(viewModel.currentCycleIndex.value + 1)

                                println("INDEX DEL CICLO DEL ORTO " + viewModel.currentCycleIndex.value.toString())
                                println("CANTIDAD DE ELEMENTOS EN LA LISTA DE CICLOS DE MIERDA " + viewModel.uiState.cycles!!.size.toString())
                                /* Verifico si llegue al final de mi rutina comparando el currentCycleIndex con la cantidad total de ciclos en el vector de ciclos*/
                                if (viewModel.currentCycleIndex.value >= viewModel.uiState.cycles!!.size) {
                                    println("ENTRE LA CONCHA DE TU MADRE" + viewModel.uiState.cycles!!.size.toString())
                                    navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                                    typeOfExecution = 9
                                }
                                else {
                                    println("SOY UN FORRO Y NO ENTRE" + viewModel.uiState.cycles!!.size.toString())
                                    viewModel.setExerciseIndex(0)
                                    val cycle = viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value)
                                    val exercise = viewModel.uiState.exercises!!.get(cycle.id)!!.get(viewModel.currentExerciseIndex.value)

                                    if (exercise.duration != 0 && exercise.repetitions != 0) {
                                        typeOfExecution = 0 // Time and Reps
                                    } else if (exercise.duration == 0) {
                                        typeOfExecution = 1 // Reps
                                    } else {
                                        typeOfExecution = 5 // Time
                                    }
                                }
                            }
                            else {
                                viewModel.setExerciseIndex(0)
                                println("ACA ESTOY POR REPETIR EL CICLO 2 ")
                                println("IndexCiclo " + viewModel.currentCycleIndex.value.toString())
                                println("IndexEjercicio " + viewModel.currentExerciseIndex.value.toString())

                                val exercise = viewModel.uiState.exercises!!.get(viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id)!!.get(viewModel.currentExerciseIndex.value)
                                println(exercise.name)
                                if (exercise.duration != 0 && exercise.repetitions != 0) {
                                    typeOfExecution = 0 // Time and Reps
                                } else if (exercise.duration == 0) {
                                    typeOfExecution = 1 // Reps
                                } else {
                                    typeOfExecution = 5 // Time
                                }
                            }
                        }
                        else { // Cambio al siguiente ejercicio
                            val exercise = viewModel.uiState.exercises!!.get(
                                viewModel.uiState.cycles!!.get(viewModel.currentCycleIndex.value).id
                            )!!.get(viewModel.currentExerciseIndex.value)
                            println(exercise.name)
                            if (exercise.duration != 0 && exercise.repetitions != 0) {
                                typeOfExecution = 0 // Time and Reps
                            } else if (exercise.duration == 0) {
                                typeOfExecution = 1 // Reps
                            } else {
                                typeOfExecution = 5 // Time
                            }
                        }
                    }) {
                    Text(
                        text = "Finalizar Ejercicio".uppercase(),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.body2,
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate(Destination.ExecutionFinishedRoutine.createRoute(routineId = routineId, totalTime = ticks))
                    }) {
                    Text(
                        text = "Finalizar Rutina".uppercase(),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
    else if (typeOfExecution == 3) {
        typeOfExecution = 0
    } else if (typeOfExecution == 4) {
        typeOfExecution = 1
    } else if(typeOfExecution == 5) {
        typeOfExecution = 2
    }

}





/* TopBar que aparece en todas las vistas de Ejecución de Rutina */
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
                contentDescription = "Imagen de Rutina",
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

/* Vista Previa de un Ejercicio */
@Composable
fun ExercisePreview() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp), uiState.routine.image, uiState.routine.name)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ciclo",
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Column(
                modifier = Modifier
                    .height(215.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                Text(text = "Abdominales".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 7,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(text = "x10".uppercase(),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(8.dp)
            )
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
                onClick = { /*TODO*/ }) {
                Text(
                    text = "Empezar",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.body2,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Inicio")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Inicio")
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(
                    text = "Finalizar Rutina",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

/* Cicle and Exercise names running at the moment */
@Composable
fun ExerciseHeader(cycle : String, exercise : String) {
    Text(text = cycle,
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

/* Circular Progress Bar */
@Composable
fun CircularProgressBar(
    percentage: Float = 1f,
    fontSize: TextUnit = 24.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colors.primary,
    stokeWidth: Dp = 16.dp,
    animDurationSec: Int = 10000,
    animDelay: Int = 0
) {
    val sb = java.lang.StringBuilder()
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    var lastPercentage by remember {
        mutableStateOf(1f)
    }

    var lastTime by remember {
        mutableStateOf(0)
    }

    var lastAnimationDuration by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
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
            delayMillis = animDelay,
            easing = LinearEasing
        )
    )

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
        Text(
            text = sb.append(TimeUnit.SECONDS.toMinutes((animDurationSec - curTime.value).toLong()).toString())
                .append(":")
                .append(TimeUnit.SECONDS.toSeconds(((animDurationSec - curTime.value) % 60).toLong()).toString()).toString(),
            color = MaterialTheme.colors.primary,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
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
        Text(
            text = "PARAR EL TIMER".uppercase(),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2,
        )
    }
}

//@Composable
//fun ExerciseScreenFinished(
//    image : String,
//    routineName : String,
//) {
//    Column(modifier = Modifier
//        .fillMaxHeight()
//        .fillMaxWidth()
//        .padding(horizontal = 16.dp, vertical = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp), image, routineName)
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
//        ) {
//            Text(text = "¡Terminó!".uppercase(),
//                color = MaterialTheme.colors.primaryVariant,
//                fontSize = 40.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                overflow = TextOverflow.Ellipsis,
//                maxLines = 1
//            )
//            Column(
//                modifier = Modifier
//                    .height(120.dp)
//                    .width(264.dp)
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp, horizontal = 4.dp)
//                    .clip(shape = RoundedCornerShape(16.dp))
//                    .background(MaterialTheme.colors.primaryVariant),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//            ) {
//                Text(text = "01:23:43",
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    overflow = TextOverflow.Ellipsis,
//                    maxLines = 1
//                )
//                Text(text = "Tiempo Utilizado".uppercase(),
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    overflow = TextOverflow.Ellipsis,
//                    maxLines = 1
//                )
//            }
//            Column(
//                modifier = Modifier
//                    .height(120.dp)
//                    .width(264.dp)
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp, horizontal = 4.dp)
//                    .clip(shape = RoundedCornerShape(16.dp))
//                    .background(MaterialTheme.colors.primaryVariant),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
//            ) {
//                Text(text = "Calificá la rutina",
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    overflow = TextOverflow.Ellipsis,
//                    maxLines = 1
//                )
////                RatingBar(
////                    rating = 2,
////                    starsColor = MaterialTheme.colors.primary,
////                    modifier = Modifier.padding(bottom = 2.dp)
////                )
//            }
//        }
//        Column(
//            modifier = Modifier
//                .padding(horizontal = 64.dp)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
//                shape = RoundedCornerShape(16.dp),
//                onClick = { /*TODO*/ }) {
//                Text(text = "Finalizar".uppercase(),
//                    color = MaterialTheme.colors.primaryVariant,
//                    style = MaterialTheme.typography.body2,
//                )
//            }
//        }
//    }
//}

@Composable
fun FinishedExecutionScreen (
    routineId : Int,
    totalTime : Int,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel(factory = getExecuteViewModelFactory())
){
    val uiState = viewModel.uiState
    val sb = java.lang.StringBuilder()

    if (!uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
    }

    var hours = totalTime/3600
    var minutes = totalTime/60
    var seconds = totalTime
    if (hours >= 1) {
        minutes -= hours * 60
        seconds -= hours * 3600
        if (minutes % 60 >= 1) {
            seconds -= minutes * 60
        } else {
            seconds -= hours * 3600
        }
    } else {
        if (minutes % 60 >= 1) {
            seconds -= minutes * 60
        }
    }

    val s = String.format("%02d : %02d : %02d", hours, minutes, seconds)

    if (!uiState.isAllFetching && uiState.routine != null) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        uiState.routine!!.image?.let {
            TopBarRoutineExecution(Modifier.padding(bottom = 16.dp),
                it, uiState.routine.name, 1f)
        }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(text = "¡Terminó!".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                ) {
                    Text(text = s,
//                    sb.append(hours.toString())
//                        .append(":")
//                        .append(minutes.toString())
//                        .append(":")
//                        .append(
//                            seconds.
//                            seconds.toString()
//                        ).toString(),
                        color = MaterialTheme.colors.primary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(text = "Tiempo estimado".uppercase() ,
                        color = MaterialTheme.colors.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                ) {
                    Text(text = "Calificá la rutina",
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
                        navController.navigate(Destination.Routine.createRoute(routineId = routineId))
                    }) {
                    Text(
                        text = "Finalizar".uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}