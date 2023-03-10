package com.erolgizlice.notesapp.core.data.repository

import com.erolgizlice.notesapp.core.model.data.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNoteList(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}