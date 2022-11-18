package com.staminapp.ui.execute

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.staminapp.ui.main.RatingBar
import com.staminapp.util.getExecuteViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.ui.main.ApiErrorDialog
import com.staminapp.ui.main.LoadingIndicator
import com.staminapp.ui.theme.Gray

@Composable
fun FinishedExecutionScreen (
    routineId : Int,
    totalTime : Int,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel(factory = getExecuteViewModelFactory())
){
    val uiState = viewModel.uiState

    if (uiState.message == null && !uiState.isFetching && uiState.routine == null) {
        viewModel.getRoutine(routineId)
    }

    val hours = totalTime/3600
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

    if (uiState.message != null) {
        ApiErrorDialog {
            viewModel.getRoutine(routineId)
        }
    } else if (uiState.routine == null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingIndicator()
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.routine.image?.let {
                TopBarRoutineExecution(
                    Modifier.padding(bottom = 16.dp),
                    it, uiState.routine.name, 1f)
            }
            Column(
                modifier = Modifier.padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(text = stringResource(R.string.finished).uppercase(),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .width(264.dp)
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(Gray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                ) {
                    Text(text = stringResource(R.string.estimated_time).uppercase() ,
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = s,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Column(
                    modifier = Modifier
                        .width(264.dp)
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                ) {
                    Text(text = stringResource(R.string.rate_routine).uppercase(),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.subtitle1,
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
                    .width(264.dp)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.popBackStack()
                    }) {
                    Text(
                        text = stringResource(R.string.finish).uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
    }
}