package com.staminapp

import android.accounts.AuthenticatorDescription
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExploreScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .horizontalScroll(rememberScrollState())) {
            FilterIcon()
            CustomChip(selected = false, text = "Novato", modifier = Modifier.padding(10.dp))
            CustomChip(selected = false, text = "Intermedio", modifier = Modifier.padding(10.dp))
            CustomChip(selected = false, text = "Experto", modifier = Modifier.padding(10.dp))
            CustomChip(selected = false, text = "Profesional", modifier = Modifier.padding(10.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp)) {
            val painter = painterResource(R.drawable.logo)
            Box(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(5.dp)) {
                ExercisePreview(painter, "EJERCICIO", "EJERCICIO")
            }
            val painter2 = painterResource(R.drawable.tincho2)
            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(5.dp)) {
                ExercisePreview(painter2, "EJERCICIO", "EJERCICIO")
            }
            }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp)) {
            val painter = painterResource(R.drawable.logo)
            Box(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(5.dp)) {
                ExercisePreview(painter, "EJERCICIO", "EJERCICIO")
            }
            val painter2 = painterResource(R.drawable.tincho2)
            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(5.dp)) {
                ExercisePreview(painter2, "EJERCICIO", "EJERCICIO")
            }
        }
    }
}

@Composable
fun ExercisePreview(painter: Painter,
                    contentDescription: String,
                    title:String,
                    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}
@Composable
fun FilterIcon(){
    IconButton(modifier = Modifier.padding(10.dp),onClick = {
    }) {
        Icon(Icons.Filled.List, contentDescription = "Filtros", tint = Gray)
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