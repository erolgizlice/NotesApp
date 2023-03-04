package com.erolgizlice.notesapp.feature.addeditnote

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolgizlice.notesapp.core.domain.NoteUseCases
import com.erolgizlice.notesapp.core.model.data.AddEditNoteEvent
import com.erolgizlice.notesapp.core.model.data.InvalidNoteException
import com.erolgizlice.notesapp.core.model.data.Note
import com.erolgizlice.notesapp.core.model.data.TodoNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentNote = mutableStateOf(Note())
    val currentNote = _currentNote

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private val _todoNoteContent = mutableStateListOf(TodoNote())
    val todoNoteContent: SnapshotStateList<TodoNote> = _todoNoteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _notePinned = mutableStateOf(false)
    val notePinned: State<Boolean> = _notePinned

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    private var recentlyDeletedNote: Note? = null

    var textFromSpeech: String? by mutableStateOf(null)

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentNote.value = note
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                        _todoNoteContent.clear()
                        _todoNoteContent.addAll(note.todoContent)
                        _noteColor.value = note.color
                        _notePinned.value = note.isPinned
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = event.value
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = event.value
            }
            is AddEditNoteEvent.EnteredTodoContent -> {
                _todoNoteContent[event.index] = todoNoteContent[event.index].copy(
                    content = event.value
                )
            }
            is AddEditNoteEvent.ChangeCheckBox -> {
                _todoNoteContent[event.index] = todoNoteContent[event.index].copy(
                    isChecked = event.isChecked
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        if ((noteTitle.value.isNotEmpty() && noteTitle.value.isNotBlank()) ||
                            (noteContent.value.isNotEmpty() && noteContent.value.isNotBlank() ||
                                    (todoNoteContent.isNotEmpty() && todoNoteContent.any { it.content.isNotEmpty() }))
                        ) {
                            noteUseCases.addNoteUseCase(
                                Note(
                                    title = noteTitle.value,
                                    content = noteContent.value,
                                    todoContent = todoNoteContent,
                                    timestamp = System.currentTimeMillis(),
                                    color = noteColor.value,
                                    id = currentNoteId,
                                    isPinned = notePinned.value
                                )
                            )
                        }
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
            is AddEditNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                    _eventFlow.emit(UiEvent.DeleteNote)
                }
            }
            is AddEditNoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is AddEditNoteEvent.CopyNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(
                        Note(
                            title = noteTitle.value,
                            content = noteContent.value,
                            todoContent = todoNoteContent,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                            isPinned = notePinned.value
                        )
                    )
                    _eventFlow.emit(UiEvent.CopyNote)
                }
            }
            is AddEditNoteEvent.PinNote -> {
                _notePinned.value = !_notePinned.value
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
        object DeleteNote : UiEvent()
        object CopyNote : UiEvent()
    }

    data class TodoNoteTextFieldState(
        var text: String = "",
        var isChecked: Boolean = false
    )
}
