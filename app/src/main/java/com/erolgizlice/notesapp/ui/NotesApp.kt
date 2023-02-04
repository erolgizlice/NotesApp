package com.erolgizlice.notesapp.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erolgizlice.notesapp.core.data.util.NetworkMonitor
import com.erolgizlice.notesapp.navigation.NotesNavHost
import com.erolgizlice.notesapp.navigation.NotesTabs
import com.erolgizlice.notesapp.ui.theme.BlackGrey
import com.erolgizlice.notesapp.ui.theme.WhiteContent

@Composable
fun NotesApp(
    networkMonitor: NetworkMonitor,
    appState: NotesAppState = rememberNotesAppState(networkMonitor)
) {
    Surface {
        val snackbarHostState = remember { SnackbarHostState() }

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    "You are not connected to the internet",
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        Scaffold(
            contentColor = MaterialTheme.colorScheme.onBackground,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 20.dp),
                    onClick = { /* TODO */ },
                    shape = FloatingActionButtonDefaults.shape,
                    containerColor = BlackGrey,
                    contentColor = WhiteContent,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                NotesBottomBar(
                    destinations = appState.notesTabs,
                    onNavigateToDestination = appState::navigateToNotesDestination,
                )
            }
        ) { paddingValues ->
            Surface {
                NotesNavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = appState.navController,
                    onBackClick = appState::onBackClick
                )
            }
        }
    }
}

@Composable
fun NotesBottomBar(
    destinations: List<NotesTabs>,
    onNavigateToDestination: (NotesTabs) -> Unit,
) {
    val density = LocalDensity.current
    val shapeSize = density.run { 70.dp.toPx() }

    val cutCornerShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 20.dp,
        bottomEnd = 20.dp
    )
    val outline = cutCornerShape.createOutline(
        Size(shapeSize, shapeSize),
        LocalLayoutDirection.current,
        density
    )
    BottomAppBar(
        modifier = Modifier
            .height(50.dp)
            .drawWithContent {
                with(drawContext.canvas.nativeCanvas) {

                    val checkPoint = saveLayer(null, null)
                    val width = size.width

                    val outlineWidth = outline.bounds.width
                    val outlineHeight = outline.bounds.height

                    // Destination
                    drawContent()

                    // Source
                    withTransform(
                        {
                            translate(
                                left = ((width - outlineWidth) / 1.03).toFloat(),
                                top = (-outlineHeight / 1.5).toFloat()
                            )
                        }
                    ) {
                        drawOutline(
                            outline = outline,
                            color = Color.Transparent,
                            blendMode = BlendMode.Clear
                        )
                    }

                    restoreToCount(checkPoint)
                }
            },
        containerColor = BlackGrey,
        contentColor = WhiteContent,
        content = {

            destinations.forEach { destination ->
                val text = stringResource(id = destination.title)

                IconButton(
                    onClick = { onNavigateToDestination(destination) },
                ) {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = text
                    )
                }
            }
        }
    )
}