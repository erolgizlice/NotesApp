package com.erolgizlice.notesapp.core.model.data

import com.erolgizlice.notesapp.core.designsystem.theme.*

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long
) {

    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)