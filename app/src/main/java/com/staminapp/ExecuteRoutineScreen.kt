package com.staminapp

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.theme.StaminappAppTheme
import java.util.concurrent.TimeUnit

/* TopBar que aparece en todas las vistas de Ejecución de Rutina */
@Composable
fun TopBarRoutineExecution(modifier: Modifier) {
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
                painter = painterResource(id = R.drawable.tincho2),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
            )
            Text(text = "a todo ritmo me encanta coldplay pa la puta madre".uppercase(),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        LinearProgressIndicator(progress = 0.3f, modifier = Modifier.fillMaxWidth())
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
        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp))
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
                Text(text = "Empezar",
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
                Text(text = "Finalizar Rutina",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

/* Cicle and Exercise names running at the moment */
@Composable
fun ExerciseHeader() {
    Text(text = "Ciclo",
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    Text(text = "Ejercicio",
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
    val animationDuration by remember {
        mutableStateOf(animDurationSec*1000)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) 0f else 1f,
        animationSpec = tween (
            durationMillis = animationDuration,
            delayMillis = 0,
            easing = LinearEasing
        )
    )
    val curTime = animateIntAsState(
        targetValue = if (animationPlayed) animDurationSec else 0,
        animationSpec = tween (
            durationMillis = animationDuration,
            delayMillis = animDelay,
            easing = LinearEasing
        )
    )
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

/* Ejercicio con Tiempo */
@Composable
fun ExerciseScreenTime() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseHeader()
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
                CircularProgressBar(percentage = 1f, animDurationSec = 70, radius = 80.dp, fontSize = 40.sp)
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
                onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Pause, contentDescription = "Inicio")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar Ejercicio".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.body2,
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar Rutina".uppercase(),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

/* Ejercicio con Repeticiones */
@Composable
fun ExerciseScreenReps() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseHeader()
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
                Text(text = "Reps",
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
                onClick = { /*TODO*/ }) {
                Text(text = "Listo",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.body2,
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar Rutina".uppercase(),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

/* Ejercicio con Tiempo y Repeticiones */
@Composable
fun ExerciseScreenRepsAndTime() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseHeader()
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
                CircularProgressBar(percentage = 1f, animDurationSec = 70, stokeWidth = 12.dp, radius = 50.dp, fontSize = 20.sp)
                Text(text = "Reps",
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
                onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Pause, contentDescription = "Inicio")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar Ejercicio".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.body2,
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar Rutina".uppercase(),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ExerciseScreenFinished() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarRoutineExecution(Modifier.padding(bottom = 16.dp))
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
                Text(text = "01:23:43",
                    color = MaterialTheme.colors.primary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(text = "Tiempo Utilizado".uppercase(),
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
                    rating = 2,
                    starsColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 2.dp)
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
                onClick = { /*TODO*/ }) {
                Text(text = "Finalizar".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    StaminappAppTheme {
        ExerciseScreenTime()
    }
}