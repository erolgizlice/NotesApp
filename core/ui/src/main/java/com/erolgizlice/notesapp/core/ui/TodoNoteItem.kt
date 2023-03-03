package com.erolgizlice.notesapp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import com.erolgizlice.notesapp.core.model.data.TodoNote

@Composable
fun TodoNoteItem(
    modifier: Modifier,
    title: String,
    contentList: List<TodoNote>,
    color: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (title.isNotEmpty())
                Text(
                    text = title,
                    color = WhiteContent
                )
            contentList.forEach { todoNote ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (todoNote.isChecked) Icons.Outlined.CheckBox
                        else Icons.Outlined.CheckBoxOutlineBlank,
                        contentDescription = null,
                        tint = WhiteContent
                    )
                    Text(
                        text = todoNote.content,
                        color = WhiteContent
                    )
                }
            }
        }
    }
}