package com.erolgizlice.notesapp.feature.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.erolgizlice.notesapp.core.designsystem.theme.SearchColor
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent

@Composable
internal fun NotesRoute(
    modifier: Modifier = Modifier,
    state: SearchState = rememberSearchState()
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { state.query = it },
            searchFocused = state.focused,
            onSearchFocusChange = { state.focused = it },
            onClearQuery = {
                focusManager.clearFocus()
                state.query = TextFieldValue("")
                state.focused = false
            },
            searching = state.searching
        )
        Card(
            modifier = modifier.padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = WhiteContent
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Title")
                Text(text = "Content")
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SearchColor,
        contentColor = WhiteContent,
        shape = RoundedCornerShape(38.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            tint = WhiteContent,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = {/*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            tint = WhiteContent,
                            contentDescription = null
                        )
                    }
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        },
                    cursorBrush = SolidColor(WhiteContent),
                    textStyle = MaterialTheme.typography.body1.copy(color = WhiteContent)
                )
                if (!searchFocused) {
                    Row {
                        IconButton(onClick = {/*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.ViewAgenda,
                                tint = WhiteContent,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {/*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                tint = WhiteContent,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight()
    ) {
        Spacer(Modifier.width(48.dp))
        Text(
            text = "Search your notes",
            color = WhiteContent
        )
    }
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<Note> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<Note>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)
}

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long
)

private val allNotes = listOf(
    Note(
        id = 1,
        title = "asdasdasd",
        content = "asdasdsfsdfsdf",
        color = 1,
        timestamp = 1
    ),
    Note(
        id = 1,
        title = "asdasdasd",
        content = "asdasdsfsdfsdf",
        color = 1,
        timestamp = 1
    ),
    Note(
        id = 1,
        title = "asdasdasd",
        content = "asdasdsfsdfsdf",
        color = 1,
        timestamp = 1
    ),
    Note(
        id = 1,
        title = "asdasdasd",
        content = "asdasdsfsdfsdf",
        color = 1,
        timestamp = 1
    )
)