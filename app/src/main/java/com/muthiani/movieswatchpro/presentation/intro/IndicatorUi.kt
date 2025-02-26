package com.muthiani.movieswatchpro.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun indicatorUi(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = MoviesWatchProTheme.colors.iconInteractive,
    unselectedColor: Color = MoviesWatchProTheme.colors.iconInteractiveInactive,
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) {
            Box(
                modifier =
                    Modifier
                        .height(14.dp)
                        .width(if (it == currentPage) 24.dp else 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.CenterVertically)
                        .background(color = if (it == currentPage) selectedColor else unselectedColor),
            )

            Spacer(modifier = Modifier.width(2.5.dp))
        }
    }
}

@Preview
@Composable
fun indicator1() {
    indicatorUi(pageSize = 3, currentPage = 1)
}

@Preview
@Composable
fun indicator2() {
    indicatorUi(pageSize = 3, currentPage = 2)
}

@Preview
@Composable
fun indicator3() {
    indicatorUi(pageSize = 3, currentPage = 3)
}
