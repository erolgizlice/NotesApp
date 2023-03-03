package com.erolgizlice.notesapp.core.database.util

import androidx.room.TypeConverter
import com.erolgizlice.notesapp.core.database.model.TodoNoteEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoNoteListConverter {

    @TypeConverter
    fun todoNoteListToString(value: List<TodoNoteEntity>) =
        Json.encodeToString(value)

    @TypeConverter
    fun stringToTodoNoteList(encodedString: String) : List<TodoNoteEntity> =
        Json.decodeFromString(encodedString)
}