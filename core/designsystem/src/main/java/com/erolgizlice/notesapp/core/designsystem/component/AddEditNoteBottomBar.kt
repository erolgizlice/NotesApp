package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditNoteBottomBar(
    date: String?,
    onColorClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onColorClick) {
            Icon(
                imageVector = Icons.Outlined.Palette,
                tint = WhiteContent,
                contentDescription = null
            )
        }
        date?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Edited $it",
                color = WhiteContent
            )
        }
        IconButton(
            onClick = onSettingsClick
        ) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                tint = WhiteContent,
                contentDescription = null
            )
        }
    }
}
