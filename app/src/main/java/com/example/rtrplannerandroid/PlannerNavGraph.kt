package com.example.rtrplannerandroid

import android.app.Activity
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rtrplannerandroid.PlannerDestinationsArgs.EVENT_ID_ARG
import com.example.rtrplannerandroid.PlannerDestinationsArgs.TITLE_ARG
import com.example.rtrplannerandroid.ui.DailySummaryScreen
import com.example.rtrplannerandroid.ui.EditEventScreen
import com.example.rtrplannerandroid.ui.EventListScreen
import com.example.rtrplannerandroid.ui.components.AppModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlannerNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = PlannerDestinations.EVENT_LIST_ROUTE,
    navActions: PlannerNavigationActions = remember(navController) {
        PlannerNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            PlannerDestinations.DAILY_SUMMARY_ROUTE
        ) {
            AppModalDrawer(drawerState, currentRoute, navActions) {
                DailySummaryScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } })
            }
        }
        composable(
            PlannerDestinations.EVENT_LIST_ROUTE
        ) {
            AppModalDrawer(drawerState, currentRoute, navActions) {
                EventListScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onAddEvent = { navActions.navigateToEditEvent(R.string.add_event, null)})

            }
        }
        composable(
            PlannerDestinations.EDIT_EVENT_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType},
                navArgument(EVENT_ID_ARG) { type = NavType.StringType; nullable = true},
            )
        )
        {entry ->
            EditEventScreen(
                title = entry.arguments?.getInt(TITLE_ARG)!!,
                onUpdate = {
                           navActions.navigateToEventList()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }

}