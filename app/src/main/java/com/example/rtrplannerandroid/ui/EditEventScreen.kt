package com.example.rtrplannerandroid.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rtrplannerandroid.R
import com.example.rtrplannerandroid.ui.components.EditEventTopAppBar
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.datepicker.data.model.DatePickerDate
import com.vsnappy1.timepicker.TimePicker
import com.vsnappy1.timepicker.data.model.TimePickerTime
import java.util.Calendar


@Composable
fun EditEventScreen(
    @StringRes title: Int,
    onUpdate: () -> Unit,
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
            viewModel::updateTime,
            modifier = Modifier.padding(paddingValues)
        )

        //Navigate back on save
        LaunchedEffect(uiState.isSaved) {
            if (uiState.isSaved) {
                onUpdate()
            }
        }
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
    onDateChanged: (Int, Int, Int) -> Unit,
    onTimeChanged: (Int, Int) -> Unit,
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
        MyDatePicker(
            eventDate = eventDate,
            onDateChanged = onDateChanged
        )
        MyTimePicker(
            eventDate = eventDate,
            onTimeChanged = onTimeChanged)


    }
}


@Composable
fun MyDatePicker(
    eventDate: Calendar,
    onDateChanged: (Int, Int, Int) -> Unit
) {
    DatePicker(
        onDateSelected = onDateChanged,
        date = DatePickerDate(
            year = eventDate.get(Calendar.YEAR),
            month = eventDate.get(Calendar.MONTH),
            day = eventDate.get(Calendar.DAY_OF_MONTH))
    )
}

@Composable
fun MyTimePicker(
    eventDate: Calendar,
    onTimeChanged: (Int, Int) -> Unit
) {
    TimePicker(
        onTimeSelected = onTimeChanged,
        time = TimePickerTime(
            hour = eventDate.get(Calendar.HOUR_OF_DAY),
            minute = eventDate.get(Calendar.MINUTE)
        )
    )
}

@Preview
@Composable
private fun EditEventPreview() {
    EditEventScreen(R.string.add_event, {}, {})
}