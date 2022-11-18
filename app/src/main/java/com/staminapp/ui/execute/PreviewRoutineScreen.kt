package com.staminapp.ui.execute

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.staminapp.util.translateDifficultyForApp

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
    if (uiState.routine != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                Card(
                    modifier = Modifier.width(160.dp).height(160.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 5.dp
                ) {
                    Image(
                        bitmap = decodeBase64Image(uiState.routine.image).asImageBitmap(),
                        contentDescription = stringResource(R.string.routine_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                CustomChip(selected = false,
                    text = translateDifficultyForApp(string = uiState.routine.difficulty),
                    modifier = Modifier
                )
                Text(text = uiState.routine.name.uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h3,
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
                        1 -> navController.navigate(Destination.ExecutionRoutineScreen2.createRoute(routineId = id))
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.start).uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.button,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFBD5D1)),
                shape = RoundedCornerShape(16.dp),
                onClick = { navController.popBackStack() }) {
                Text(
                    text = stringResource(R.string.cancel).uppercase(),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}