package com.erolgizlice.notesapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erolgizlice.notesapp.core.model.data.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long? = null
)

fun NoteEntity.asExternalModel() = Note(
    id = id,
    title = title,
    content = content,
    color = color,
    timestamp = timestamp
)