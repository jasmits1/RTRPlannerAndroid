package com.example.rtrplannerandroid.ui

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.rtrplannerandroid.R

@Composable
fun EditEventScreen(
    @StringRes title: Int
) {
    Text(
        text = stringResource(id = title)
    )
}

@Preview
@Composable
private fun EditEventPreview() {
    EditEventScreen(R.string.add_event)
}