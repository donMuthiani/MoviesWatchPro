package com.muthiani.movieswatchpro.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun Header() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = MoviesWatchProTheme.colors.brand),
        ) {
            Image(
                modifier =
                    Modifier
                        .padding(vertical = 64.dp, horizontal = 16.dp)
                        .align(Alignment.CenterStart),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun bottomPanel() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MoviesWatchProTheme.colors.brand),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customHomeTopBar(showActions: Boolean = true) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showMenu by remember { mutableStateOf(false) }

    MediumTopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MoviesWatchProTheme.colors.brand,
            ),
        title = {
            Image(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = "Logo",
            )
        },
        actions = {
            if (showActions) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search movies",
                        tint = Color.White,
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "user",
                        tint = Color.White,
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        onClick = { /* Handle Option 1 */ },
                        text = { Text(text = "Option 1") },
                    )
                    DropdownMenuItem(
                        onClick = { /* Handle Option 2 */ },
                        text = { Text(text = "Option 2") },
                    )
                    DropdownMenuItem(
                        onClick = { /* Handle Option 3 */ },
                        text = { Text(text = "Option 2") },
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
