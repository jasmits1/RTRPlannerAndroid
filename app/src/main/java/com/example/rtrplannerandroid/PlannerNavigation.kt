package com.example.rtrplannerandroid

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.rtrplannerandroid.PlannerDestinationsArgs.EVENT_ID_ARG
import com.example.rtrplannerandroid.PlannerDestinationsArgs.TITLE_ARG
import com.example.rtrplannerandroid.PlannerScreens.EVENT_LIST_SCREEN
import com.example.rtrplannerandroid.PlannerScreens.DAILY_SUMMARY_SCREEN
import com.example.rtrplannerandroid.PlannerScreens.EDIT_EVENT_SCREEN

private object PlannerScreens {
    const val EVENT_LIST_SCREEN = "eventList"
    const val DAILY_SUMMARY_SCREEN = "dailySummary"
    const val EDIT_EVENT_SCREEN = "editEvent"
}

object PlannerDestinationsArgs {
    const val EVENT_ID_ARG = "eventId"
    const val TITLE_ARG = "title"
}

object PlannerDestinations {
    const val EVENT_LIST_ROUTE = EVENT_LIST_SCREEN
    const val DAILY_SUMMARY_ROUTE = DAILY_SUMMARY_SCREEN
    const val EDIT_EVENT_ROUTE = "$EDIT_EVENT_SCREEN/{$TITLE_ARG}?$EVENT_ID_ARG={$EVENT_ID_ARG}"
}

class PlannerNavigationActions(private val navController: NavHostController) {
    fun navigateToDailySummary() {
        navController.navigate(PlannerDestinations.DAILY_SUMMARY_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToEventList() {
        navController.navigate(PlannerDestinations.EVENT_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = false
            }

            launchSingleTop = true
            restoreState = false
        }
    }

    fun navigateToEditEvent(title: Int, eventId: String?) {
        navController.navigate(
            "$EDIT_EVENT_SCREEN/$title".let {
                if (eventId != null) "$it?$EVENT_ID_ARG=$eventId" else it
            }
        )
    }
}