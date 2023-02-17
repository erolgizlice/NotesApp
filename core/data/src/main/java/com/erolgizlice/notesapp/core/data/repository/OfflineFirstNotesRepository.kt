package com.erolgizlice.notesapp.core.data.repository

import com.erolgizlice.notesapp.core.data.model.asEntityModel
import com.erolgizlice.notesapp.core.database.dao.NoteDao
import com.erolgizlice.notesapp.core.database.model.NoteEntity
import com.erolgizlice.notesapp.core.database.model.asExternalModel
import com.erolgizlice.notesapp.core.model.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstNotesRepository @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepository {

    override fun getNoteList(): Flow<List<Note>> =
        noteDao.getNoteEntityList()
            .map { it.map(NoteEntity::asExternalModel) }

    override suspend fun getNote(id: Int): Note? =
        noteDao.getNoteEntity(id)?.asExternalModel()

    override suspend fun insertNote(note: Note) {
        noteDao.insertNoteEntity(note.asEntityModel())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNoteEntity(note.asEntityModel())
    }
}