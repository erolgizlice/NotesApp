package com.erolgizlice.notesapp.core.database.dao

import androidx.room.*
import com.erolgizlice.notesapp.core.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getNoteEntityList(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteEntity(noteId: String): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteEntity(note: NoteEntity)

    @Delete
    suspend fun deleteNoteEntity(note: NoteEntity)
}