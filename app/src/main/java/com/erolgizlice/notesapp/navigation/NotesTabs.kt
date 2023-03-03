package com.erolgizlice.notesapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.ui.graphics.vector.ImageVector
import com.erolgizlice.notesapp.R

enum class NotesTabs(
    val icon: ImageVector,
    @StringRes val title: Int,
    val route: String,
) {
    TODO(
        icon = Icons.Outlined.CheckBox,
        title = R.string.todo,
        route = NotesDestinations.TODO_ROUTE
    ),
    MIC(
        icon = Icons.Outlined.Mic,
        title = R.string.mic,
        route = NotesDestinations.MIC_ROUTE
    ),
    GALLERY(
        icon = Icons.Outlined.Image,
        title = R.string.gallery,
        route = NotesDestinations.GALLERY_ROUTE
    )
}

private object NotesDestinations {
    const val TODO_ROUTE = "notes/todo"
    const val MIC_ROUTE = "notes/mic"
    const val GALLERY_ROUTE = "notes/gallery"
}

object MainNotesDestinations {
    const val NOTES_ROUTE = "notes"
    const val NOTE_DETAIL_ROUTE = "note"
    const val NOTE_DETAIL_ID_ROUTE = "noteId"
}