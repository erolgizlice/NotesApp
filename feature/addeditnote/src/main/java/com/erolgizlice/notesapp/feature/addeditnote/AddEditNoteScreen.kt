package com.erolgizlice.notesapp.feature.addeditnote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.component.AddEditNoteTopAppBar
import com.erolgizlice.notesapp.core.designsystem.component.TextFieldHint
import com.erolgizlice.notesapp.core.designsystem.theme.LightGrey
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun AddEditNoteRoute(
    modifier: Modifier = Modifier
) {
    AddEditNoteScreen(
        modifier = modifier
    )
}

@Composable
fun AddEditNoteScreen(modifier: Modifier) {
    var query by remember { mutableStateOf("") }
    var contentQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AddEditNoteTopAppBar()
        Box(
            Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            if (query.isEmpty()) {
                TextFieldHint(
                    placeholder = stringResource(id = R.string.placeholder_title),
                    textStyle = Typography.titleLarge.copy(color = LightGrey),
                )
            }
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                cursorBrush = SolidColor(WhiteContent),
                textStyle = Typography.titleLarge.copy(color = WhiteContent)
            )
        }
        Box(
            Modifier
                .weight(1f)
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            if (contentQuery.isEmpty()) {
                TextFieldHint(
                    placeholder = stringResource(id = R.string.placeholder_note),
                    textStyle = Typography.bodyLarge.copy(color = LightGrey),
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxSize(),
                value = contentQuery,
                onValueChange = { contentQuery = it },
                cursorBrush = SolidColor(WhiteContent),
                textStyle = Typography.bodyLarge.copy(color = WhiteContent)
            )
        }
    }
}
