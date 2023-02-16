package com.erolgizlice.notesapp.core.domain

import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val getNoteListUseCase: GetNoteListUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)
