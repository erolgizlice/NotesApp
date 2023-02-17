package com.erolgizlice.notesapp.core.model.data

import androidx.compose.ui.focus.FocusState


sealed class AddEditNoteEvent {

    data class EnteredTitle(val value: String) : AddEditNoteEvent()

    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()

    data class EnteredContent(val value: String) : AddEditNoteEvent()

    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()

    data class ChangeColor(val color: Int) : AddEditNoteEvent()

    object SaveNote : AddEditNoteEvent()

    data class DeleteNote(val note: Note): AddEditNoteEvent()

    object RestoreNote: AddEditNoteEvent()

    object CopyNote: AddEditNoteEvent()
}
