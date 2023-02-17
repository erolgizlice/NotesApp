package com.erolgizlice.notesapp.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun NoteItem(
    modifier: Modifier,
    title: String,
    content: String,
    color: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (title.isNotEmpty())
                Text(
                    text = title,
                    color = WhiteContent
                )
            if (content.isNotEmpty())
                Text(
                    text = content,
                    color = WhiteContent
                )
        }
    }
}