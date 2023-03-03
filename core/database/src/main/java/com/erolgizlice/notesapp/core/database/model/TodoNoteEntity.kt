package com.erolgizlice.notesapp.core.database.model

import com.erolgizlice.notesapp.core.model.data.TodoNote

@kotlinx.serialization.Serializable
data class TodoNoteEntity(
    val content: String,
    val isChecked: Boolean
)

fun TodoNoteEntity.asExternalModel() = TodoNote(
    content = content,
    isChecked = isChecked
)