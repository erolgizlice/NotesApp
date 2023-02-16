package com.erolgizlice.notesapp.feature.addeditnote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erolgizlice.notesapp.core.designsystem.component.AddEditNoteTopAppBar
import com.erolgizlice.notesapp.core.designsystem.component.TextFieldHint
import com.erolgizlice.notesapp.core.designsystem.theme.LightGrey
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditNoteRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    onBackClick()
                }
            }
        }
    }

    AddEditNoteScreen(
        modifier = modifier,
        titleState = titleState,
        contentState = contentState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditNoteScreen(
    modifier: Modifier,
    titleState: AddEditNoteViewModel.NoteTextFieldState,
    contentState: AddEditNoteViewModel.NoteTextFieldState,
    onEvent: (AddEditNoteViewModel.AddEditNoteEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AddEditNoteTopAppBar(
            onEventSaveNote = { onEvent(AddEditNoteViewModel.AddEditNoteEvent.SaveNote) }
        )
        Box(
            Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            if (titleState.text.isEmpty()) {
                TextFieldHint(
                    placeholder = stringResource(id = R.string.placeholder_title),
                    textStyle = Typography.titleLarge.copy(color = LightGrey),
                )
            }
            BasicTextField(
                value = titleState.text,
                onValueChange = {
                    onEvent(AddEditNoteViewModel.AddEditNoteEvent.EnteredTitle(it))
                },
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
            if (contentState.text.isEmpty()) {
                TextFieldHint(
                    placeholder = stringResource(id = R.string.placeholder_note),
                    textStyle = Typography.bodyLarge.copy(color = LightGrey),
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxSize(),
                value = contentState.text,
                onValueChange = {
                    onEvent(AddEditNoteViewModel.AddEditNoteEvent.EnteredContent(it))
                },
                cursorBrush = SolidColor(WhiteContent),
                textStyle = Typography.bodyLarge.copy(color = WhiteContent)
            )
        }
    }
}
