package com.muthiani.movieswatchpro.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun ErrorScreen(
    errorMessage: String = "",
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(text = "Error", style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
        },
        text = {
            Text(text = errorMessage, style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
        },
        confirmButton = {
            MoviesWatchButton(onClick = { onDismiss() }) {
                Text(text = "OK", style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
            }
        },
        containerColor = MoviesWatchProTheme.colors.error,
    )
}

@Composable
fun SuccessScreen(
    message: String = "",
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Success", style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
        },
        text = {
            Text(text = message, style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
        },
        confirmButton = {
            MoviesWatchButton(onClick = { onDismiss() }) {
                Text(text = "OK", style = TextStyle(color = MoviesWatchProTheme.colors.textInteractive))
            }
        },
        containerColor = MoviesWatchProTheme.colors.brand,
    )
}
