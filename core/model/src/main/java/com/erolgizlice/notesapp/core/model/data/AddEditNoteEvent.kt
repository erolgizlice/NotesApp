package com.erolgizlice.notesapp.core.model.data


sealed class AddEditNoteEvent {

    data class EnteredTitle(val value: String) : AddEditNoteEvent()

    data class EnteredContent(val value: String) : AddEditNoteEvent()

    data class EnteredTodoContent(val index: Int, val value: String) : AddEditNoteEvent()

    data class ChangeCheckBox(val index: Int, val isChecked: Boolean) : AddEditNoteEvent()

    data class ChangeColor(val color: Int) : AddEditNoteEvent()

    object SaveNote : AddEditNoteEvent()

    data class DeleteNote(val note: Note): AddEditNoteEvent()

    object RestoreNote: AddEditNoteEvent()

    object CopyNote: AddEditNoteEvent()
    object PinNote: AddEditNoteEvent()
}
