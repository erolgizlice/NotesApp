package com.erolgizlice.notesapp.core.designsystem.component

import android.view.KeyEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun TodoTextField(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onEventEnteredContent: (String) -> Unit,
    onEventChecked: (Boolean) -> Unit,
    onDeleteClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onEventChecked(!isChecked) }) {
            Icon(
                imageVector = if (isChecked) Icons.Outlined.CheckBox
                else Icons.Outlined.CheckBoxOutlineBlank,
                contentDescription = null,
                tint = WhiteContent
            )
        }
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        onAddClicked()
                        true
                    }
                    false
                },
            value = text,
            onValueChange = onEventEnteredContent,
            cursorBrush = SolidColor(WhiteContent),
            textStyle = Typography.bodyLarge.copy(color = WhiteContent),
            singleLine = true
        )
        IconButton(onClick = onDeleteClicked) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                tint = WhiteContent
            )
        }
    }
}