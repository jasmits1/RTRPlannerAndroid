package com.example.rtrplannerandroid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rtrplannerandroid.PlannerDestinations
import com.example.rtrplannerandroid.PlannerNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.rtrplannerandroid.R;

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: PlannerNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToEventList = { navigationActions.navigateToEventList() },
                navigateToDailySummary = { navigationActions.navigateToDailySummary() },
                closeDrawer = { coroutineScope.launch { drawerState.close() } })
        }) {
        content()
    }
}

@Composable
private fun AppDrawer(
    currentRoute: String,
    navigateToEventList: () -> Unit,
    navigateToDailySummary: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerButton(
            label = stringResource(id = R.string.event_list_title),
            isSelected = currentRoute == PlannerDestinations.EVENT_LIST_ROUTE,
            action = {
                navigateToEventList()
                closeDrawer()
            }
        )
        DrawerButton(
            label = stringResource(id = R.string.daily_summary_title),
            isSelected = currentRoute == PlannerDestinations.DAILY_SUMMARY_ROUTE,
            action = {
                navigateToDailySummary()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerButton(
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Outlined.List, stringResource(id = R.string.event_list_title))
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = tintColor
            )
        }
    }
}

@Preview
@Composable
fun PreviewAppDrawer() {
    Surface {
        AppDrawer(
            currentRoute = PlannerDestinations.EVENT_LIST_ROUTE,
            navigateToEventList = {  },
            navigateToDailySummary = {  },
            closeDrawer = {  })
    }
}