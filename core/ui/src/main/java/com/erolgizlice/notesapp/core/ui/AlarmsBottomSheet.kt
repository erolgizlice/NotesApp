package com.erolgizlice.notesapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.erolgizlice.notesapp.core.designsystem.component.RowButton
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlarmsBottomSheet(
    color: Color,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    onLaterTodayClick: () -> Unit,
    onTomorrowMorningClick: () -> Unit,
    onPickDateClick: () -> Unit,
    onPickPlaceClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        RowButton(
            onClick = onLaterTodayClick,
            text = "Later today",
            endText = "6:00 PM",
            icon = Icons.Outlined.Schedule,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onTomorrowMorningClick,
            text = "Tomorrow morning",
            endText = "8:00 AM",
            icon = Icons.Outlined.Schedule,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onPickDateClick,
            text = "Pick a date & time",
            icon = Icons.Outlined.Schedule,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onPickPlaceClick,
            text = "Pick a place",
            icon = Icons.Outlined.LocationOn,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
    }
}