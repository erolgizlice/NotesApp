package com.erolgizlice.notesapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
fun SettingsBottomSheet(
    color: Color,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    onDeleteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        RowButton(
            onClick = onDeleteClick,
            text = "Delete",
            icon = Icons.Outlined.Delete,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onCopyClick,
            text = "Make a copy",
            icon = Icons.Outlined.CopyAll,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onShareClick,
            text = "Send",
            icon = Icons.Outlined.Share,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
        RowButton(
            onClick = onHelpClick,
            text = "Help & feedback",
            icon = Icons.Outlined.HelpOutline,
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState
        )
    }
}
