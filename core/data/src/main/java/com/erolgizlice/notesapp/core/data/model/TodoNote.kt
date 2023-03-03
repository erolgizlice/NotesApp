package com.erolgizlice.notesapp.core.data.model

import com.erolgizlice.notesapp.core.database.model.TodoNoteEntity
import com.erolgizlice.notesapp.core.model.data.TodoNote

fun TodoNote.asEntityModel() = TodoNoteEntity(
    content = content,
    isChecked = isChecked
)