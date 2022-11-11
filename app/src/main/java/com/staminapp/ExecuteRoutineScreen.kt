package com.staminapp

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.staminapp.ui.theme.StaminappAppTheme

@Composable
fun StartExecutionScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                painter = painterResource(id = R.drawable.tincho2),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
            )
            CustomChip(selected = false, text = "Principiante", modifier = Modifier)
            Text(text = "a todo ritmo me encanta coldplay pa la puta madre".uppercase(),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
            onClick = { /*TODO*/ }) {
            Text(text = "Cancelar",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

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
            modifier = Modifier.padding(horizontal = 64.dp).fillMaxSize(),
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