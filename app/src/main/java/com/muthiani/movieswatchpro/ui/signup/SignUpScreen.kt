package com.muthiani.movieswatchpro.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.ui.intro.ButtonUi
import com.muthiani.movieswatchpro.utils.ConstantUtils


@Composable
fun SignUpScreen(
    onNavigateHome: () -> Unit = {},
    onGoogleSignIn: () -> Unit
) {

    Scaffold(topBar = { Header() }, content =
    {
        Column(modifier = Modifier.padding(it)) {
            Content()
        }

    }, bottomBar = {
        BottomPanel()
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
            .verticalScroll(state = rememberScrollState())
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

        BottomViews()

        Spacer(modifier = Modifier.size(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            ButtonUi(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                text = "Sign In",
                textColor = Color.White,
                backgroundColor = Color.Black) {
            }

            Spacer(modifier = Modifier.size(24.dp))

            GoogleSignInButton {
                googleSignIn()
            }
        }
        
        Spacer(modifier = Modifier.size(24.dp))

        NoAccountSignUp()
    }

}

fun forgotPasword() {

}

 fun googleSignIn() {


}

@Composable
fun BottomViews() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.fillMaxWidth(), ) {
        Box(modifier = Modifier.wrapContentSize()) {
            Checkbox(checked = isChecked, onCheckedChange = {isChecked = !isChecked})
        }

        Box(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.CenterVertically)
            .weight(1f)) {
            Text(text = "Remember for 30 days",
                style = MaterialTheme.typography.bodyMedium)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.dp)
            .align(Alignment.CenterVertically)
            .weight(1f),
            contentAlignment = Alignment.CenterEnd)
        {
            TextButton(onClick = { forgotPasword() }) {
                Text(text = "Forgot Password")
            }
        }

    }
}

@Composable
fun NoAccountSignUp() {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Don't have an account?")
        TextButton(onClick = {  }) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun BottomPanel() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .background(color = MaterialTheme.colorScheme.primary))
}



@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen {  }
}