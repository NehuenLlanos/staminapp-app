package com.staminapp

import android.content.Intent
import android.view.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.theme.Gray
import com.staminapp.ui.theme.StaminappAppTheme
import kotlin.math.ceil
import kotlin.math.floor
import androidx.compose.material.Icon as Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp

@Composable
fun ExecuteRoutine2Screen(navController: NavController) {
    Scaffold() {
        Column(
            modifier = Modifier
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
                    painter = painterResource(id = R.drawable.tincho2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Text(text = "a todo ritmo me encanta coldplay pa la puta madre".uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(12) { i ->
                    ExecutionExercise(
                        idx = i,
                        title = "Ejecrcicio $i",
                        cycle = "Ciclo $i", selected = i == 2,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x11000000)
                        )
                    ))
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .background(White)
                    .padding(16.dp)
            ) {
                Text(
                    "00:50",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "x10",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.fillMaxWidth()
                )
                ExecutionButtons()
            }

        }

    }

}

@Composable
fun ExecutionButtons(
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(16.dp),
        onClick = {

        }
    ) {
        Icon(
            Icons.Filled.Pause,
            contentDescription = null,
            tint = White,
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD28C)),
            shape = RoundedCornerShape(16.dp),
            onClick = {

            }
        ) {
            Icon(
                Icons.Filled.FastRewind,
                contentDescription = null,
                tint = White,
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD28C)),
            shape = RoundedCornerShape(16.dp),
            onClick = {

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

        }
    ) {
        Text(text = "Finalizar Rutina".uppercase(),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.button,
        )
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
            .background(when {
                selected -> MaterialTheme.colors.primaryVariant
                else -> Gray
            })
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

@Preview
@Composable
fun ExecuteRoutine2Preview() {
    StaminappAppTheme {
        ExecuteRoutine2Screen(rememberNavController())
    }
}