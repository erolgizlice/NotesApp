package com.erolgizlice.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.erolgizlice.notesapp.feature.notes.navigation.notesNavigationRoute
import com.erolgizlice.notesapp.feature.notes.navigation.notesScreen
import com.erolgizlice.notesapp.feature.todo.navigation.todoScreen

@Composable
fun NotesNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = notesNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        notesScreen()
        todoScreen()
    }
}