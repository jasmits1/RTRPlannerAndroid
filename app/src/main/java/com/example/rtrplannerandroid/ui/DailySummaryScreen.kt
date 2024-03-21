package com.example.rtrplannerandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rtrplannerandroid.R
import com.example.rtrplannerandroid.ui.components.EventListContent
import com.example.rtrplannerandroid.ui.components.EventListTopAppBar

@Composable
fun DailySummaryScreen(
    openDrawer: () -> Unit,
    viewModel: DailySummaryViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EventListTopAppBar (
                R.string.daily_summary_title,
                openDrawer = openDrawer
            )
        },
        modifier = Modifier.fillMaxSize()
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
private fun DailySummaryPreview() {
    DailySummaryScreen(openDrawer = { })
}