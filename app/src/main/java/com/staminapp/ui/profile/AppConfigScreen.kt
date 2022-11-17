package com.staminapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staminapp.R
import com.staminapp.ui.theme.Gray
import com.staminapp.util.SessionManager

@Composable
fun AppConfigScreen(
    sessionManager: SessionManager,
    navController: NavController
) {
    var showRecents by remember { mutableStateOf(sessionManager.getShowRecent()) }
    var dropdownsOpen by remember { mutableStateOf(sessionManager.getDropdownsOpen()) }
    var executionMode by remember { mutableStateOf(sessionManager.getExecutionMode()) }

    val executionOptions = listOf(
        stringResource(R.string.immersive_view),
        stringResource(R.string.detailed_view)
    )

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_configuration))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                stringResource(R.string.home_screen),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.show_recent_routines),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant
                )
                Switch(
                    checked = showRecents,
                    onCheckedChange = {
                        sessionManager.setShowRecent(it)
                        showRecents = it
                    },
                    colors = SwitchDefaults.colors(MaterialTheme.colors.primary)
                )
            }
            Spacer(modifier = Modifier
                .padding(vertical = 16.dp)
                .height(4.dp)
                .fillMaxWidth()
                .background(Gray))

            Text(
                stringResource(R.string.routine_view),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.dropdowns_open),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant
                )
                Switch(
                    checked = dropdownsOpen,
                    onCheckedChange = {
                        sessionManager.setDropdownsOpen(it)
                        dropdownsOpen = it
                    },
                    colors = SwitchDefaults.colors(MaterialTheme.colors.primary)
                )
            }
            Spacer(modifier = Modifier
                .padding(vertical = 16.dp)
                .height(4.dp)
                .fillMaxWidth()
                .background(Gray))

            Text(
                stringResource(R.string.execution_mode),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Column(Modifier.selectableGroup()) {
                executionOptions.forEachIndexed { index, text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = executionMode == index,
                                onClick = {
                                    sessionManager.setExecutionMode(index)
                                    executionMode = index
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = executionMode == index,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary)
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}