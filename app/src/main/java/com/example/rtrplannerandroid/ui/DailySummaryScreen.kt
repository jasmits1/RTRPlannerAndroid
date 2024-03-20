package com.example.rtrplannerandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rtrplannerandroid.ui.components.EventListTopAppBar

@Composable
fun DailySummaryScreen(
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EventListTopAppBar (
                openDrawer = openDrawer
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Text(
            text = "Hello from Daily Summary Screen",
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
private fun DailySummaryPreview() {
    DailySummaryScreen(openDrawer = { })
}