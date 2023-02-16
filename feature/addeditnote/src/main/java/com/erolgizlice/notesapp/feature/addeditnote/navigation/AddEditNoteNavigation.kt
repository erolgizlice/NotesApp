package com.erolgizlice.notesapp.feature.addeditnote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.erolgizlice.notesapp.feature.addeditnote.AddEditNoteRoute

const val addEditNoteNavigationRoute = "add_edit_note_route"

fun NavController.navigateToAddEditNote(navOptions: NavOptions? = null) {
    this.navigate(addEditNoteNavigationRoute, navOptions)
}

fun NavGraphBuilder.addEditNoteScreen(
    onBackClick: () -> Unit
) {
    composable(route = addEditNoteNavigationRoute) {
        AddEditNoteRoute(onBackClick = onBackClick)
    }
}