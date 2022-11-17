package com.staminapp.ui.explore

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.util.getExploreViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.staminapp.R
import com.staminapp.ui.main.CustomChip
import com.staminapp.ui.main.RoutineCard
import com.staminapp.util.getDifficultyApiStringFromIndex

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ExploreViewModel = viewModel(factory = getExploreViewModelFactory())
) {
    val uiState = viewModel.uiState
    if (!uiState.isFetching && uiState.routines == null) {
        viewModel.getAllRoutines()
    }

    var expanded by remember { mutableStateOf(false) }
    var order by remember { mutableStateOf(0) }
    val difficulties = listOf(
        stringResource(R.string.rookie),
        stringResource(R.string.beginner),
        stringResource(R.string.intermediate),
        stringResource(R.string.advanced),
        stringResource(R.string.expert)
    )

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState()),
                ) {
                    difficulties.forEachIndexed { index, it ->
                        CustomChip(
                            selected = uiState.selectedDifficulties[index],
                            text = it,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    if (uiState.selectedDifficulties[index]) {
                                        viewModel.unselectDifficulty(index);
                                    } else {
                                        viewModel.selectDifficulty(index);
                                    }
                                }
                        )
                    }
                }
                Box {
                    Icon(
                        Icons.Default.Sort,
                        contentDescription = stringResource(R.string.order_by),
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                expanded = !expanded
                            }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                order = 0
                                expanded = false
                            }
                        ) {
                            Text(stringResource(R.string.by_name))
                        }
                        DropdownMenuItem(
                            onClick = {
                                order = 1
                                expanded = false
                            }
                        ) {
                            Text(stringResource(R.string.by_date))
                        }
                        DropdownMenuItem(
                            onClick = {
                                order = 2
                                expanded = false
                            }
                        ) {
                            Text(stringResource(R.string.by_rating))
                        }
                    }
                }
            }

        }

        if (uiState.displayedRoutines != null) {
            items(
                when (order) {
                    0 -> uiState.displayedRoutines.sortedBy { it.name }
                    1 -> uiState.displayedRoutines.sortedBy { it.date }
                    2 -> uiState.displayedRoutines.sortedBy { it.score }
                    else -> uiState.displayedRoutines
                }
            ) {
                RoutineCard(navController = navController, routine = it)
            }
        }
    }
}