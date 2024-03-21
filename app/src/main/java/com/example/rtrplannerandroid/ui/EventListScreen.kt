package com.example.rtrplannerandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rtrplannerandroid.R;
import com.example.rtrplannerandroid.ui.components.EventListTopAppBar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EventListScreen(
    openDrawer: () -> Unit,
    onAddEvent: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EventListTopAppBar (
                openDrawer = openDrawer
            )
        },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            //FloatingActionButton(onClick = onAddEvent) {
            FloatingActionButton(onClick = viewModel::addTestEvent) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_event))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        if(uiState.events.isNotEmpty()) {
            Text(
                text = uiState.events[0].eventDate.time.toString(),
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Preview
@Composable
private fun EventListPreview() {
    EventListScreen({}, {})
}