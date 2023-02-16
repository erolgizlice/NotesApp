package com.erolgizlice.notesapp.core.domain

import com.erolgizlice.notesapp.core.data.repository.NotesRepository
import javax.inject.Inject

class GetNoteListUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    operator fun invoke() = notesRepository.getNoteList()
}