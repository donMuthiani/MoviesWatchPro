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
import com.muthiani.movieswatchpro.ui.components.BottomPanel
import com.muthiani.movieswatchpro.ui.components.CustomHomeTopBar
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun HomeScreen(navController: NavController) {


    MoviesWatchProTheme {
        Scaffold(topBar = { CustomHomeTopBar() }, content = { innerpadding ->
            Column(modifier = Modifier.padding(innerpadding)) {
                ScrollContent(innerpadding)
            }
        }, bottomBar = {
            BottomPanel()

        })
    }
}

@Composable
fun ScrollContent(innerpadding: PaddingValues) {

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}