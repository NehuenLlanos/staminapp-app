package com.staminapp

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.staminapp.ui.theme.Gray
import com.staminapp.ui.theme.StaminappAppTheme
import androidx.compose.material.Icon as Icon

@Composable
fun RoutineScreen(navController: NavController) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "¡Mirá esta rutina en StaminApp! https://www.staminapp.com/routine/1")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Compartí esta rutina")
    val context = LocalContext.current

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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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