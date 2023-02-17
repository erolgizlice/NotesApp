package com.erolgizlice.notesapp.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.erolgizlice.notesapp.feature.notes.NotesRoute

const val notesNavigationRoute = "notes_route"

fun NavController.navigateToNotes(navOptions: NavOptions? = null) {
    this.navigate(notesNavigationRoute, navOptions)
}

fun NavGraphBuilder.notesScreen(
    onNoteClick: (Int, Int) -> Unit
) {
    composable(route = notesNavigationRoute) {
        NotesRoute(
            onNoteClick = onNoteClick
        )
    }
}