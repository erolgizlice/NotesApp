package com.erolgizlice.notesapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erolgizlice.notesapp.core.database.dao.NoteDao
import com.erolgizlice.notesapp.core.database.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}