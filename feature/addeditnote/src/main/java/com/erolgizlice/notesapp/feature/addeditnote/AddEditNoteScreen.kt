package com.erolgizlice.notesapp.feature.addeditnote

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erolgizlice.notesapp.core.data.util.getDateTime
import com.erolgizlice.notesapp.core.data.util.setAlarm
import com.erolgizlice.notesapp.core.designsystem.component.*
import com.erolgizlice.notesapp.core.designsystem.theme.LightGrey
import com.erolgizlice.notesapp.core.designsystem.theme.Typography
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import com.erolgizlice.notesapp.core.model.data.AddEditNoteEvent
import com.erolgizlice.notesapp.core.model.data.Note
import com.erolgizlice.notesapp.core.model.data.TodoNote
import com.erolgizlice.notesapp.core.ui.AlarmsBottomSheet
import com.erolgizlice.notesapp.core.ui.ColorsBottomSheet
import com.erolgizlice.notesapp.core.ui.SettingsBottomSheet
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditNoteRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteColor: Int,
    isTodoNote: Boolean,
    isMic: Boolean
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val todoContentState = remember { viewModel.todoNoteContent }
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

    val context = LocalContext.current
    var clickToShowPermission by rememberSaveable { mutableStateOf(true) }

    if (clickToShowPermission && isMic) {
        OpenVoiceWithPermission(
            context = context,
            onEvent = viewModel::onEvent
        ) { clickToShowPermission = false }
    }

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
        isPinned = isPinned,
        isTodoNote = isTodoNote,
        todoContentState = todoContentState
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AddEditNoteScreen(
    modifier: Modifier,
    titleState: String,
    contentState: String,
    onEvent: (AddEditNoteEvent) -> Unit,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    color: Animatable<Color, AnimationVector4D>,
    note: Note,
    scope: CoroutineScope,
    isPinned: Boolean,
    isTodoNote: Boolean,
    todoContentState: SnapshotStateList<TodoNote>,
) {
    val context = LocalContext.current
    var bottomSheetDisplay by remember { mutableStateOf(BottomSheetDisplay.Settings) }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            when (bottomSheetDisplay) {
                BottomSheetDisplay.Colors -> {
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
                }
                BottomSheetDisplay.Settings -> {
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
                BottomSheetDisplay.Alarms -> {
                    AlarmsBottomSheet(
                        color = color.value,
                        coroutineScope = coroutineScope,
                        modalSheetState = modalSheetState,
                        onLaterTodayClick = {
                            setAlarm(
                                context = context,
                                calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                    set(Calendar.HOUR_OF_DAY, 18)
                                    set(Calendar.MINUTE, 0)
                                },
                                title = note.title,
                                content = note.content
                            )
                        },
                        onTomorrowMorningClick = {
                            setAlarm(
                                context = context,
                                calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                    add(Calendar.DATE, 1)
                                    set(Calendar.HOUR_OF_DAY, 8)
                                    set(Calendar.MINUTE, 0)
                                },
                                title = note.title,
                                content = note.content
                            )
                        },
                        onPickDateClick = {},
                        onPickPlaceClick = {}
                    )
                }
            }
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            AddEditNoteTopAppBar(
                isPinned = isPinned,
                onBackClick = { onEvent(AddEditNoteEvent.SaveNote) },
                onPinClick = { onEvent(AddEditNoteEvent.PinNote) },
                onAlarmClick = {
                    coroutineScope.launch {
                        bottomSheetDisplay = BottomSheetDisplay.Alarms
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
            )
            Box(
                Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                if (titleState.isEmpty()) {
                    TextFieldHint(
                        placeholder = stringResource(id = R.string.placeholder_title),
                        textStyle = Typography.titleLarge.copy(color = LightGrey),
                    )
                }
                BasicTextField(
                    value = titleState,
                    onValueChange = {
                        onEvent(AddEditNoteEvent.EnteredTitle(it))
                    },
                    cursorBrush = SolidColor(WhiteContent),
                    textStyle = Typography.titleLarge.copy(color = WhiteContent)
                )
            }
            if (isTodoNote) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(todoContentState) { index, todoNote: TodoNote ->
                        TodoTextField(
                            text = todoNote.content,
                            isChecked = todoNote.isChecked,
                            onEventEnteredContent = {
                                onEvent(AddEditNoteEvent.EnteredTodoContent(index, it))
                            },
                            onEventChecked = {
                                onEvent(AddEditNoteEvent.ChangeCheckBox(index, it))
                            },
                            onDeleteClicked = {
                                todoContentState.remove(todoNote)
                            },
                            onAddClicked = {
                                todoContentState.add(TodoNote())
                            }
                        )
                    }

                    item {
                        RowButton(
                            onClick = { todoContentState.add(TodoNote()) },
                            text = stringResource(id = R.string.label_list_item),
                            icon = Icons.Outlined.Add
                        )
                    }
                }
            } else {
                NoteTextField(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp),
                    onEventEnteredContent = { onEvent(AddEditNoteEvent.EnteredContent(it)) },
                    text = contentState,
                    placeHolder = stringResource(id = R.string.placeholder_note)
                )
            }
            AddEditNoteBottomBar(
                date = getDateTime(note.timestamp, "dd/MM/yyyy"),
                onColorClick = {
                    coroutineScope.launch {
                        bottomSheetDisplay = BottomSheetDisplay.Colors
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
            ) {
                coroutineScope.launch {
                    bottomSheetDisplay = BottomSheetDisplay.Settings
                    if (modalSheetState.isVisible)
                        modalSheetState.hide()
                    else
                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
    }
}

enum class BottomSheetDisplay {
    Colors, Settings, Alarms
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenVoiceWithPermission(
    context: Context,
    onEvent: (AddEditNoteEvent) -> Unit,
    finished: () -> Unit
) {

    val voicePermissionState = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    if (voicePermissionState.status.isGranted) {
        startSpeechToText(context, finished = finished, onEvent = onEvent)
    } else {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "Please grant mic permission") },
            confirmButton = {
                Button(onClick = { voicePermissionState.launchPermissionRequest() }) {
                    Text(text = "Request permission")
                }
            }
        )
    }
}

fun startSpeechToText(
    context: Context,
    finished: () -> Unit,
    onEvent: (AddEditNoteEvent) -> Unit
) {
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
    )
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr_TR")

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(bundle: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(v: Float) {}
        override fun onBufferReceived(bytes: ByteArray?) {}
        override fun onEndOfSpeech() {
            finished()
        }

        override fun onError(i: Int) {}

        override fun onResults(bundle: Bundle) {
            val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (result != null) {
                onEvent(AddEditNoteEvent.EnteredContent(result[0]))
            }
        }

        override fun onPartialResults(bundle: Bundle) {}
        override fun onEvent(i: Int, bundle: Bundle?) {}

    })
    speechRecognizer.startListening(speechRecognizerIntent)
}