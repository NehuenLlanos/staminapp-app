package com.staminapp

import android.content.Intent
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

@Composable
fun RoutineScreen(navController: NavController) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "¡Mirá esta rutina en StaminApp! https://www.staminapp.com/routine/1")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Compartí esta rutina")
    val context = LocalContext.current

    var isDescriptionOpen by remember { mutableStateOf(true) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Rutina") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(shareIntent)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Compartir", tint = White)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton (
                onClick = {

                },
                icon = {
                    Icon(Icons.Filled.FitnessCenter, contentDescription = null)
                },
                text = { Text("Empezar".uppercase()) },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.tincho2),
                        contentDescription = "Round corner image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f / 1f)
                            .clip(RoundedCornerShape(10))
                    )
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        CustomChip(selected = false, text = "Principiante")
                        Text("A todo Ritmo".uppercase(),
                            style = MaterialTheme.typography.h2,
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
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text("Calificación",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.primaryVariant
                    )
                    RatingBar(
                        rating = 1,
                        starsColor = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isDescriptionOpen = !isDescriptionOpen
                        }
                ) {

                    Text("Descripción",
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
                        " hola".repeat(50),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.body1
                    )

                }
                Spacer(modifier = Modifier
                    .padding(top = 8.dp)
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(Gray))
                Text("Ciclos",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(6) { i ->
                Cycle(
                    title = "Ciclo $i",
                    repetitions = i,
                    exercises = listOf("Ejercicio 1", "Ejercicio 2", "Ejercicio 3"),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }

}

@Composable
fun Cycle(
    modifier: Modifier = Modifier,
    title: String,
    repetitions: Int,
    exercises: List<String>
) {
    var isOpen by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .border(1.5.dp, MaterialTheme.colors.primaryVariant, RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpen = !isOpen
                }
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            exercise,
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            exercise,
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    Row(modifier = modifier) {
        repeat(rating) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = starsColor)
        }
        repeat(stars - rating) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = Gray)
        }
    }
}

@Composable
fun CustomChip(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.primaryVariant
            else -> Gray
        },
        contentColor = when {
            selected -> White
            else -> MaterialTheme.colors.primaryVariant
        },
        shape = CircleShape,
        modifier = modifier,
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(12.dp, 8.dp)
        )
    }
}

@Preview
@Composable
fun ComposablePreview() {
    StaminappAppTheme {
        RoutineScreen(rememberNavController())
    }
}