package com.erolgizlice.notesapp.core.domain

import com.erolgizlice.notesapp.core.data.repository.NotesRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    operator fun invoke(noteId: String) = notesRepository.getNote(noteId)
}