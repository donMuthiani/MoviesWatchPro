package com.muthiani.movieswatchpro.presentation.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun MoviesWatchDivider(
    modifier: Modifier = Modifier,
    color: Color = MoviesWatchProTheme.colors.uiBorder.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

private const val DividerAlpha = 0.12f
