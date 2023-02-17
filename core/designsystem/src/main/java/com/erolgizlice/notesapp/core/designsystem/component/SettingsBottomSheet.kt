package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick()
            coroutineScope.launch { modalSheetState.hide() }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = WhiteContent
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = text, color = WhiteContent)
        }
    }
}
