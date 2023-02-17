package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun AddEditNoteTopAppBar(
    isPinned: Boolean,
    onBackClick: () -> Unit,
    onPinClick: () -> Unit,
) {
    Row {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                tint = WhiteContent,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onPinClick) {
                Icon(
                    imageVector = if (isPinned)
                        Icons.Filled.PushPin
                    else
                        Icons.Outlined.PushPin,
                    tint = WhiteContent,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.AddAlert,
                    tint = WhiteContent,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Archive,
                    tint = WhiteContent,
                    contentDescription = null
                )
            }
        }
    }
}