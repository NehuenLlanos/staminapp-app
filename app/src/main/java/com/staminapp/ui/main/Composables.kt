package com.staminapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.data.model.Routine
import com.staminapp.ui.theme.Gray
import com.staminapp.util.decodeBase64Image

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    rate: (score: Int) -> Unit
) {
    var mutableRating by remember { mutableStateOf(rating) }

    Row(modifier = modifier) {
        repeat(mutableRating) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.clickable {
                    rate(it + 1)
                    mutableRating = it + 1
                }
            )
        }
        repeat(stars - mutableRating) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Gray,
                modifier = Modifier.clickable {
                    rate(it + mutableRating + 1)
                    mutableRating += it + 1
                }
            )
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
            selected -> Color.White
            else -> MaterialTheme.colors.primaryVariant
        },
        modifier = modifier.clip(CircleShape),
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(12.dp, 8.dp)
        )
    }
}

@Composable
fun RoutineCard(navController: NavController, routine: Routine, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable{
            navController.navigate(Destination.Routine.createRoute(routine.id))
        },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f / 1f)
        ) {
            Image(
                bitmap = decodeBase64Image(routine.image).asImageBitmap(),
                contentDescription = "Imagen de Rutina",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
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
                Text(
                    routine.name,
                    color = MaterialTheme.colors.background,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}