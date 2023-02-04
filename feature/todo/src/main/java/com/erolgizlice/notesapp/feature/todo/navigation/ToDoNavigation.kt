package com.erolgizlice.notesapp.feature.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.erolgizlice.notesapp.feature.todo.ToDoRoute

const val toDoNavigationRoute = "todo_route"

fun NavController.navigateToTODO(navOptions: NavOptions? = null) {
    this.navigate(toDoNavigationRoute, navOptions)
}

fun NavGraphBuilder.todoScreen() {
    composable(route = toDoNavigationRoute) {
        ToDoRoute()
    }
}