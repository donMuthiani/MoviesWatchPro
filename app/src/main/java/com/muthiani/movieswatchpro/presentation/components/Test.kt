package com.muthiani.movieswatchpro.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.R

@Composable
fun TestComponent() {
    Box(
        modifier =
            Modifier
                .size(100.dp)
                .background(Color.LightGray),
    ) {
        Image(
            contentDescription = "",
            painter = painterResource(R.drawable.ic_explore),
            modifier =
                Modifier
                    .offset(20.dp, 20.dp)
                    .border(3.dp, color = Color.Blue)
                    .fillMaxHeight(),
        )
    }
}

@Preview
@Composable
fun TestComponentPreview() {
    TestComponent()
}
