package com.example.movieswatchpro.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieswatchpro.R



@Composable
fun SignUpScreen(

) {

    Scaffold(topBar = { Header() }, content =
    {
        Column(modifier = Modifier.padding(it)) {
            Content()
        }
    })

}

@Composable
fun Header() {
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)) {
            Image(modifier = Modifier
                .padding(vertical = 64.dp, horizontal = 16.dp)
                .align(Alignment.CenterStart),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = null)
        }
    }
}

@Composable
fun Content() {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.size(50.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )

        Text(
            text = "Please enter your details",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(value = email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it },
            label = { Text(text = "Email address")},
            isError = isError
        )

        OutlinedTextField(value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password = it },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(text = "Enter password")},
            isError = isError,
            trailingIcon = {
                val image = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )

        if(isError) {
            Text(text = "Error: Invalid email",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp))
        }
    }

}




@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}