package com.example.rtrplannerandroid.ui

import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rtrplannerandroid.R
import com.example.rtrplannerandroid.ui.components.EditEventTopAppBar
import com.example.rtrplannerandroid.ui.components.EventListTopAppBar
import java.util.Calendar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditEventScreen(
    @StringRes title: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditEventViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            EditEventTopAppBar(
                title = title,
                onSave = viewModel::save,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        EditEventContent(
            uiState.title,
            uiState.description,
            uiState.location,
            uiState.eventDate,
            uiState.isLoading,
            viewModel::updateTitle,
            viewModel::updateDescription,
            viewModel::updateLocation,
            viewModel::updateDate,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun EditEventContent(
    title: String,
    description: String,
    location: String,
    eventDate: Calendar,
    isLoading: Boolean,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (Calendar) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
            .verticalScroll(rememberScrollState())
        ) {
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high)
        )
        OutlinedTextField(
            value = title,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onTitleChanged,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.title_hint),
                    style = MaterialTheme.typography.h6
                )
            },
            textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            colors = textFieldColors
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            placeholder = { Text(stringResource(id = R.string.description_hint)) },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            colors = textFieldColors
        )
        OutlinedTextField(
            value = location,
            onValueChange = onLocationChanged,
            placeholder = { Text(stringResource(id = R.string.location_hint))},
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            colors = textFieldColors
        )
    }
}


@Composable
fun MyDatePicker(
    eventDate: Calendar
) {

    //DatePicker()
}

@Preview
@Composable
private fun EditEventPreview() {
    EditEventScreen(R.string.add_event, {})
}