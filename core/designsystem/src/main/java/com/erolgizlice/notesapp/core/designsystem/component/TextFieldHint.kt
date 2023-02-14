package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldHint(
    modifier: Modifier = Modifier,
    placeholder: String,
    textStyle: TextStyle,
    isSearch: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (isSearch) Spacer(Modifier.width(48.dp))
        Text(
            text = placeholder,
            style = textStyle
        )
    }
}