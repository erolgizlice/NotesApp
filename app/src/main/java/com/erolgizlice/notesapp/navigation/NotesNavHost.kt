package com.erolgizlice.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.erolgizlice.notesapp.feature.addeditnote.navigation.addEditNoteScreen
import com.erolgizlice.notesapp.feature.addeditnote.navigation.navigateToAddEditNote
import com.erolgizlice.notesapp.feature.notes.navigation.notesNavigationRoute
import com.erolgizlice.notesapp.feature.notes.navigation.notesScreen

@Composable
fun NotesNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = notesNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        notesScreen(
            onNoteClick = { noteId, noteColor, isTodoNote ->
                navController.navigateToAddEditNote(
                    noteId = noteId,
                    noteColor = noteColor,
                    isTodoNote = isTodoNote
                )
            }
        )
        addEditNoteScreen(
            onBackClick = navController::popBackStack
        )
    }
}