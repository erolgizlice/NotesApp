package com.erolgizlice.notesapp.feature.addeditnote

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erolgizlice.notesapp.core.data.util.getDateTime
import com.erolgizlice.notesapp.core.designsystem.component.*
import com.erolgizlice.notesapp.core.designsystem.theme.LightGrey
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import com.erolgizlice.notesapp.core.model.data.AddEditNoteEvent
import com.erolgizlice.notesapp.core.model.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditNoteRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteColor: Int
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val isPinned by viewModel.notePinned

    val scaffoldState = rememberScaffoldState()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote,
                is AddEditNoteViewModel.UiEvent.DeleteNote,
                is AddEditNoteViewModel.UiEvent.CopyNote -> {
                    onBackClick()
                }
            }
        }
    }
    AddEditNoteScreen(
        modifier = modifier.background(color = noteBackgroundAnimatable.value),
        titleState = titleState,
        contentState = contentState,
        onEvent = viewModel::onEvent,
        coroutineScope = coroutineScope,
        modalSheetState = modalSheetState,
        color = noteBackgroundAnimatable,
        scope = scope,
        note = viewModel.currentNote.value,
        isPinned = isPinned
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditNoteScreen(
    modifier: Modifier,
    titleState: AddEditNoteViewModel.NoteTextFieldState,
    contentState: AddEditNoteViewModel.NoteTextFieldState,
    onEvent: (AddEditNoteEvent) -> Unit,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    color: Animatable<Color, AnimationVector4D>,
    note: Note,
    scope: CoroutineScope,
    isPinned: Boolean
) {
    val context = LocalContext.current
    val isColor = remember { mutableStateOf(false) }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            if (isColor.value) {
                ColorsBottomSheet(
                    colorList = Note.noteColors,
                    selectedColor = color.value,
                    onColorClick = {
                        scope.launch {
                            color.animateTo(
                                targetValue = Color(it),
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        }
                        onEvent(AddEditNoteEvent.ChangeColor(it))
                    }
                )
            } else {
                SettingsBottomSheet(
                    color = color.value,
                    coroutineScope = coroutineScope,
                    modalSheetState = modalSheetState,
                    onDeleteClick = {
                        onEvent(AddEditNoteEvent.DeleteNote(note))
                    },
                    onCopyClick = {
                        onEvent(AddEditNoteEvent.CopyNote)
                    },
                    onShareClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${note.title}\n${note.content}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    onHelpClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        val data: Uri =
                            Uri.parse("mailto:?subject=${note.title}&body=${note.content}&to=erolgizlice@gmail.com")
                        intent.data = data
                        context.startActivity(intent)
                    }
                )
            }
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            AddEditNoteTopAppBar(
                isPinned = isPinned,
                onBackClick = { onEvent(AddEditNoteEvent.SaveNote) },
                onPinClick = { onEvent(AddEditNoteEvent.PinNote) }
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
                        onEvent(AddEditNoteEvent.EnteredTitle(it))
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
                        onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    cursorBrush = SolidColor(WhiteContent),
                    textStyle = Typography.bodyLarge.copy(color = WhiteContent)
                )
            }
            AddEditNoteBottomBar(
                coroutineScope = coroutineScope,
                modalSheetState = modalSheetState,
                date = getDateTime(note.timestamp),
                isColor = isColor
            )
        }
    }
}
