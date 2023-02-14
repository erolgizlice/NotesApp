package com.erolgizlice.notesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.erolgizlice.notesapp.core.data.util.NetworkMonitor
import com.erolgizlice.notesapp.feature.notes.navigation.notesNavigationRoute
import com.erolgizlice.notesapp.feature.todo.navigation.navigateToTODO
import com.erolgizlice.notesapp.navigation.NotesTabs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberNotesAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): NotesAppState {
    return remember(navController, coroutineScope, networkMonitor) {
        NotesAppState(navController, coroutineScope, networkMonitor)
    }
}

class NotesAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route.equals(notesNavigationRoute)

    val notesTabs: List<NotesTabs> = NotesTabs.values().asList()

    fun navigateToNotesDestination(notesDestination: NotesTabs) {
        trace("Navigation: ${notesDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (notesDestination) {
                NotesTabs.TODO -> navController.navigateToTODO(topLevelNavOptions)
                NotesTabs.MIC -> navController.navigateToTODO(topLevelNavOptions)
                NotesTabs.GALLERY -> navController.navigateToTODO(topLevelNavOptions)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}