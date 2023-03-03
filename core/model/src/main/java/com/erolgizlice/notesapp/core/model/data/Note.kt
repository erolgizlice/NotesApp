package com.erolgizlice.notesapp.core.model.data

import com.erolgizlice.notesapp.core.designsystem.theme.*

data class Note(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val todoContent: List<TodoNote> = emptyList(),
    val color: Int = -1,
    val timestamp: Long? = null,
    val isPinned: Boolean = false
) {

    companion object {
        val noteColors = listOf(BlackNote, RedNote, BrownNote, OrangeNote, GreenNote)
    }
}

class InvalidNoteException(message: String): Exception(message)