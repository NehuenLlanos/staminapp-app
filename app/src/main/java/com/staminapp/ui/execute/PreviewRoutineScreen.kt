package com.staminapp.ui.execute

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.staminapp.R
import com.staminapp.ui.main.CustomChip
import com.staminapp.util.getExecuteViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.staminapp.ui.main.Destination
import com.staminapp.util.decodeBase64Image

/* Vista Previa para la Ejecución de una rutina */
@Composable
fun StartExecutionScreen(
    id : Int = 1,
    executionMode: Int = 0,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel ( factory = getExecuteViewModelFactory() )
) {
    val uiState = viewModel.uiState
    if (!uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(id)
        viewModel.getCyclesForRoutine(id)
        viewModel.getExercisesForCycle(id)
    }
    if (uiState.routine != null){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                Image(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp),
                    bitmap = decodeBase64Image(uiState.routine!!.image).asImageBitmap(),
                    contentDescription = "Imagen de Rutina",
                    contentScale = ContentScale.Crop
                )
                CustomChip(selected = false,
                    text =
                        if (uiState.routine.difficulty == "rookie") {
                            "novato".uppercase()
                        } else if (uiState.routine.difficulty == "beginner") {
                            "principiante".uppercase()
                        } else if (uiState.routine.difficulty == "intermediate") {
                            "intermedio".uppercase()
                        } else if (uiState.routine.difficulty == "advanced") {
                            "avanzado".uppercase()
                        } else {
                            "experto".uppercase()
                        }
                    ,
                    modifier = Modifier
                )
                Text(text = uiState.routine!!.name.uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    when (executionMode) {
                        0 -> navController.navigate(Destination.ExecutionRoutineScreen.createRoute(routineId = id))
                        1 -> navController.navigate(Destination.ExecutionRoutineScreen.createRoute(routineId = id))
                    }
                }
            ) {
                Text(
                    text = "Iniciar".uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.body2,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { navController.navigate(Destination.Routine.createRoute(routineId = id)) }) {
                Text(
                    text = "Cancelar".uppercase(),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}