package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import com.erolgizlice.notesapp.core.designsystem.theme.LightGrey
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    onEventEnteredContent: (String) -> Unit,
    text: String,
    placeHolder: String
) {
    Box(modifier = modifier) {
        if (text.isEmpty()) {
            TextFieldHint(
                placeholder = placeHolder,
                textStyle = Typography.bodyLarge.copy(color = LightGrey),
            )
        }
        BasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = text,
            onValueChange = onEventEnteredContent,
            cursorBrush = SolidColor(WhiteContent),
            textStyle = Typography.bodyLarge.copy(color = WhiteContent)
        )
    }
}