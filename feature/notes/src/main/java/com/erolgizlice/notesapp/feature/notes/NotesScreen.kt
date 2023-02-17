package com.erolgizlice.notesapp.feature.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erolgizlice.notesapp.core.designsystem.component.NoteItem
import com.erolgizlice.notesapp.core.designsystem.component.TextFieldHint
import com.erolgizlice.notesapp.core.designsystem.theme.SearchColor
import com.erolgizlice.notesapp.core.designsystem.theme.WhiteContent
import com.erolgizlice.notesapp.core.model.data.Note
import com.erolgizlice.notesapp.notes.R

@Composable
internal fun NotesRoute(
    modifier: Modifier = Modifier,
    onNoteClick: (Int, Int) -> Unit,
    viewModel: NotesViewModel = hiltViewModel(),
    searchState: SearchState = rememberSearchState()
) {
    val notesState by viewModel.state

    NotesScreen(
        modifier = modifier
            .fillMaxSize(),
        notesState = notesState,
        searchState = searchState.apply { searchResults = notesState.notes },
        onNoteClick = onNoteClick
    )
}

@Composable
fun NotesScreen(
    modifier: Modifier,
    notesState: NotesViewModel.NotesState,
    searchState: SearchState,
    onNoteClick: (Int, Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        SearchBar(
            modifier = Modifier
                .padding(16.dp),
            query = searchState.query,
            onQueryChange = { searchState.query = it },
            searchFocused = searchState.focused,
            onSearchFocusChange = { searchState.focused = it },
            onClearQuery = {
                focusManager.clearFocus()
                searchState.query = TextFieldValue("")
            },
            searching = searchState.searching
        )
        LaunchedEffect(searchState.query.text) {
            searchState.searching = true
            searchState.searchResults = notesState.notes.filter {
                it.title.contains(searchState.query.text, ignoreCase = true) ||
                        it.content.contains(searchState.query.text, ignoreCase = true)
            }
            searchState.searching = false
        }
        when (searchState.searchDisplay) {
            SearchDisplay.Categories -> {
                Card(
                    modifier = modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = WhiteContent
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Categories")
                        Text(text = "Content")
                    }
                }
            }
            SearchDisplay.Results -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(4.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(searchState.searchResults) {note ->
                        NoteItem(
                            modifier = modifier
                                .padding(4.dp)
                                .clickable {
                                    note.id?.let { onNoteClick(it, note.color) }
                                },
                            title = note.title,
                            content = note.content,
                            color = note.color
                        )
                    }
                }
            }
            SearchDisplay.NoResults -> {
                Card(
                    modifier = modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = WhiteContent
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "No Result")
                        Text(text = "Content")
                    }
                }
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
                TextFieldHint(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(),
                    placeholder = stringResource(id = R.string.placeholder_search),
                    textStyle = MaterialTheme.typography.body1.copy(color = WhiteContent),
                    isSearch = true
                )
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
    val searchDisplay: SearchDisplay
        get() = when {
            focused && query.text.isEmpty() -> SearchDisplay.Categories
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

enum class SearchDisplay {
    Categories, Results, NoResults
}