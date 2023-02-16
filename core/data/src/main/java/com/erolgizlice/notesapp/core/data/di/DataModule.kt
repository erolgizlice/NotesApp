package com.erolgizlice.notesapp.core.data.di

import com.erolgizlice.notesapp.core.data.repository.NotesRepository
import com.erolgizlice.notesapp.core.data.repository.OfflineFirstNotesRepository
import com.erolgizlice.notesapp.core.data.util.ConnectivityManagerNetworkMonitor
import com.erolgizlice.notesapp.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNotesRepository(
        notesRepository: OfflineFirstNotesRepository
    ): NotesRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}