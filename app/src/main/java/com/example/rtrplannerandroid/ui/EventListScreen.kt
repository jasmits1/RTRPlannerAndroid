package com.example.rtrplannerandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rtrplannerandroid.R;
import com.example.rtrplannerandroid.data.Event
import com.example.rtrplannerandroid.ui.components.EventListContent
import com.example.rtrplannerandroid.ui.components.EventListTopAppBar

//@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EventListScreen(
    openDrawer: () -> Unit,
    onAddEvent: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            EventListTopAppBar (
                title = R.string.event_list_title,
                openDrawer = openDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEvent) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_event))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        if(uiState.events.isNotEmpty()) {
            EventListContent(
                events = uiState.events,
                modifier = Modifier.padding(paddingValues))
        }
    }
}

@Preview
@Composable
private fun EventListPreview() {
    EventListScreen({}, {})
}