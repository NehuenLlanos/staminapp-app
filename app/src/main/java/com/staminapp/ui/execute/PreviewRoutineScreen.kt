package com.staminapp.ui.execute

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

/* Vista Previa para la Ejecución de una rutina */
@Composable
fun StartExecutionScreen(
    id : Int = 1,
    navController: NavController,
    viewModel: ExecuteViewModel = viewModel ( factory = getExecuteViewModelFactory() )
) {
    val iuState = viewModel.uiState
    if (!iuState.isFetching && iuState.routine == null) {
        viewModel.getRoutine(id)
        viewModel.getCyclesForRoutine(id)
        viewModel.getExercisesForCycle(id)
    }
    if (iuState.routine != null){
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
                onClick = {

                }) {
                Text(text = "Iniciar",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2,
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
}