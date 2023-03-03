package com.erolgizlice.notesapp.feature.addeditnote.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.erolgizlice.notesapp.feature.addeditnote.AddEditNoteRoute

const val addEditNoteNavigationRoute = "add_edit_note_route"

fun NavController.navigateToAddEditNote(
    navOptions: NavOptions? = null,
    noteId: Int = -1,
    noteColor: Int = -1,
    isTodoNote: Boolean = false
) {
    this.navigate(
        "$addEditNoteNavigationRoute?noteId=$noteId&noteColor=$noteColor&isTodoNote=$isTodoNote",
        navOptions
    )
}

fun NavGraphBuilder.addEditNoteScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$addEditNoteNavigationRoute?noteId={noteId}&noteColor={noteColor}&isTodoNote={isTodoNote}",
        arguments = listOf(
            navArgument(
                name = "noteId"
            ) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(
                name = "noteColor"
            ) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(
                name = "isTodoNote"
            ) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        val color = it.arguments?.getInt("noteColor") ?: -1
        val isTodoNote = it.arguments?.getBoolean("isTodoNote") ?: false
        AddEditNoteRoute(
            onBackClick = onBackClick,
            noteColor = color,
            isTodoNote = isTodoNote
        )
    }
}