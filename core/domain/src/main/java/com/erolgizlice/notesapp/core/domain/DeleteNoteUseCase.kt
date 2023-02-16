package com.erolgizlice.notesapp.core.domain

import com.erolgizlice.notesapp.core.data.repository.NotesRepository
import com.erolgizlice.notesapp.core.model.data.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(note: Note) = notesRepository.deleteNote(note)
}