package com.erolgizlice.notesapp.core.database.di

import com.erolgizlice.notesapp.core.database.NotesDatabase
import com.erolgizlice.notesapp.core.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesNoteDao(
        database: NotesDatabase
    ): NoteDao = database.noteDao()
}