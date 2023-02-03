package com.erolgizlice.notesapp.core.data.di

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
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}