package com.erolgizlice.notesapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.erolgizlice.notesapp.core.designsystem.theme.BlackGrey
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
fun PickDateDialog(
    onDismiss: () -> Unit
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        modifier = Modifier
            .widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        containerColor = BlackGrey,
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        title = {
            Text(
                text = "Add reminder",
                style = MaterialTheme.typography.titleLarge,
                color = WhiteContent
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DropDownDateMenuBox(
                    listItems = arrayOf("Today", "Tomorrow", "Next Saturday")
                )
                DropDownDateMenuBox(
                    listItems = arrayOf("8:00 PM", "1:00 PM", "6:00 PM", "8:00 PM")
                )
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = WhiteContent,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownDateMenuBox(listItems: Array<String>) {
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = WhiteContent,
                containerColor = BlackGrey
            )
        )

        ExposedDropdownMenu(
            modifier = Modifier.background(BlackGrey),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = selectedOption
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = WhiteContent
                    ),
                    text = {
                        Text(text = selectedOption)
                    }
                )
            }
        }
    }
}
