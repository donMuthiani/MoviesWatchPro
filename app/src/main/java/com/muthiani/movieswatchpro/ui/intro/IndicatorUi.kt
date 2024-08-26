package com.muthiani.movieswatchpro.ui.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun IndicatorUi(pageSize: Int, currentPage:Int,
                selectedColor: Color = MaterialTheme.colorScheme.secondary,
                unselectedColor: Color = MaterialTheme.colorScheme.secondaryContainer) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) {

            Box(modifier = Modifier
                .height(14.dp)
                .width(if(it == currentPage) 24.dp else 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.CenterVertically)
                .background(color = if(it == currentPage) selectedColor else unselectedColor)
            )

            Spacer(modifier = Modifier.width(2.5.dp))

        }

    }
}

@Preview
@Composable
fun Indicator1() {
    IndicatorUi(pageSize = 3, currentPage = 1)
}

@Preview
@Composable
fun Indicator2() {
    IndicatorUi(pageSize = 3, currentPage = 2)
}

@Preview
@Composable
fun Indicator3() {
    IndicatorUi(pageSize = 3, currentPage = 3)
}