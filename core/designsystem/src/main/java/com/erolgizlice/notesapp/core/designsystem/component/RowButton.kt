package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
fun RowButton(
    onClick: () -> Unit,
    text: String,
    endText: String? = null,
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
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
            endText?.let {
                Text(text = endText, color = WhiteContent)
            }
        }
    }
}