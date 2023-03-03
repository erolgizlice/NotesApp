package com.erolgizlice.notesapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erolgizlice.notesapp.core.database.dao.NoteDao
import com.erolgizlice.notesapp.core.database.model.NoteEntity
import com.erolgizlice.notesapp.core.database.util.TodoNoteListConverter

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(TodoNoteListConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}