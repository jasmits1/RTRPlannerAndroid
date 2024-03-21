package com.example.rtrplannerandroid.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.rtrplannerandroid.R;

@Composable
fun EventListTopAppBar(
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.event_list_title)) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, stringResource(id = R.string.open_drawer))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EditEventTopAppBar(
    @StringRes title: Int,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title))},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.save_event))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun EditBarPreview() {
    EditEventTopAppBar(title = R.string.add_event, onSave = { }) {
        
    }
}
@Preview
@Composable
fun EventListBarPreview() {
    EventListTopAppBar({})
}