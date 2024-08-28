package com.muthiani.movieswatchpro.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.muthiani.movieswatchpro.ui.components.bottomPanel
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun watchListScreen(navController: NavController) {
    MoviesWatchProTheme {
        Scaffold(topBar = { customHomeTopBar() }, content = { innerpadding ->
            Column(modifier = Modifier.padding(innerpadding)) {
                scrollContent(innerpadding)
            }
        }, bottomBar = {
            bottomPanel()
        })
    }
}

@Composable
fun scrollContent(innerpadding: PaddingValues) {
}

@Preview
@Composable
fun watchListScreenPreview() {
    watchListScreen(navController = NavController(LocalContext.current))
}
