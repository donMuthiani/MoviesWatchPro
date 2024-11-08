package com.muthiani.movieswatchpro.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

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
fun SearcheableTopBar() {
    var textState by remember { mutableStateOf(TextFieldValue("")) } // Initial text value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MoviesWatchProTheme.colors.brand)
            .wrapContentHeight()
    ) {
        Image(
            modifier = Modifier
                .padding(start = 24.dp, top = 48.dp)
                .align(Alignment.Start)
                .height(64.dp),
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Logo",
        )

        Spacer(modifier = Modifier.size(24.dp))

        CustomTextField(value = textState, onValueChange = { newValue -> textState = newValue })

        Spacer(modifier = Modifier.size(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, // Replace with your leading icon
                contentDescription = "Leading Icon",
                tint = Color.Black
            )
        },
        placeholder = {
            Text(text = "Search movies") // Display hint text
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.SettingsVoice, // Replace with your trailing icon
                contentDescription = "Trailing Icon",
                tint = Color.Black
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp)), // White background with rounded corners
        shape = RoundedCornerShape(24.dp), // Rounded corners for the TextField
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedPlaceholderColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black
        )
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
                modifier = Modifier.padding(vertical = 24.dp),
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
                        tint = Color.White
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "user",
                        tint = Color.White
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
