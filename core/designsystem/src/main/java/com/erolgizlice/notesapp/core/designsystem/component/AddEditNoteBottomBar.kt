package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditNoteBottomBar(
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    date: String?,
    isColor: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    isColor.value = true
                    if (modalSheetState.isVisible)
                        modalSheetState.hide()
                    else
                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }) {
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
            onClick = {
                coroutineScope.launch {
                    isColor.value = false
                    if (modalSheetState.isVisible)
                        modalSheetState.hide()
                    else
                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                tint = WhiteContent,
                contentDescription = null
            )
        }
    }
}
