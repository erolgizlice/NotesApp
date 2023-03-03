package com.erolgizlice.notesapp.core.data.model

import com.erolgizlice.notesapp.core.database.model.NoteEntity
import com.erolgizlice.notesapp.core.model.data.Note


fun Note.asEntityModel() = NoteEntity(
    id = id,
    title = title,
    content = content,
    todoContent = todoContent.map { it.asEntityModel() },
    color = color,
    timestamp = timestamp,
    isPinned = isPinned
)