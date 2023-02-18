package com.erolgizlice.notesapp.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun ColorsBottomSheet(
    selectedColor: Color,
    colorList: List<Color>,
    onColorClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(selectedColor)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = "Color",
            color = WhiteContent
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            colorList.forEach { color ->
                val isSelected = color.toArgb() == selectedColor.toArgb()
                item {
                    OutlinedButton(
                        onClick = { onColorClick(color.toArgb()) },
                        modifier = Modifier
                            .size(50.dp)
                            .padding(4.dp),
                        shape = CircleShape,
                        border = if (isSelected)
                            BorderStroke(
                                2.dp,
                                WhiteContent
                            ) else null,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = color)
                    ) {
                        if (isSelected)
                            Icon(
                                imageVector = Icons.Default.Done,
                                tint = WhiteContent,
                                contentDescription = null
                            )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}