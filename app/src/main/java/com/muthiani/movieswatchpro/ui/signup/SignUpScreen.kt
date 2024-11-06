package com.muthiani.movieswatchpro.ui.signup

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.ui.MainDestinations
import com.muthiani.movieswatchpro.ui.MoviesWatchNavController
import com.muthiani.movieswatchpro.ui.components.Header
import com.muthiani.movieswatchpro.ui.components.MoviesWatchButton
import com.muthiani.movieswatchpro.ui.components.bottomPanel
import com.muthiani.movieswatchpro.ui.rememberMoviesWatchNavController
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.utils.ConstantUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

val spacing = 24.dp

@Composable
fun SignUpScreen(navController: MoviesWatchNavController) {

//    val splashViewModel: SplashScreenViewModel = hiltViewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { Header() },
            containerColor = MoviesWatchProTheme.colors.uiBackground,
            content =
            {
                Column(modifier = Modifier.padding(it)) {
                    Content(navController)
                }
            },
            bottomBar = {
                bottomPanel()
            },
        )
    }
}

@Composable
fun Content(
    navController: MoviesWatchNavController,
//    splashViewModel: SplashScreenViewModel,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val passwordVisibleState = rememberSaveable { mutableStateOf(false) }
    val isErrorState = rememberSaveable { mutableStateOf(false) }

    var email by emailState
    var password by passwordState
    var passwordVisible by passwordVisibleState
    val isError by isErrorState

    Spacer(modifier = Modifier.size(spacing))

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp,
        )

        Text(
            text = "Please enter your details",
            style = MaterialTheme.typography.bodyMedium,
            color = MoviesWatchProTheme.colors.textSecondary,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.size(spacing))

        OutlinedTextField(
            value = email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it },
            label = { Text(text = "Email address") },
            isError = isError,
            textStyle = TextStyle(
                color = MoviesWatchProTheme.colors.textSecondary,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoviesWatchProTheme.colors.brand,
                unfocusedBorderColor = MoviesWatchProTheme.colors.brand,
                focusedLabelColor = MoviesWatchProTheme.colors.textSecondary,
                unfocusedLabelColor = MoviesWatchProTheme.colors.textSecondary,
                cursorColor = MoviesWatchProTheme.colors.textSecondary,
                errorCursorColor = MoviesWatchProTheme.colors.textSecondary,
                errorLabelColor = MoviesWatchProTheme.colors.error,

            )
        )

        OutlinedTextField(
            textStyle = TextStyle(
                color = MoviesWatchProTheme.colors.textSecondary,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoviesWatchProTheme.colors.brand,
                unfocusedBorderColor = MoviesWatchProTheme.colors.brand,
                focusedLabelColor = MoviesWatchProTheme.colors.textSecondary,
                unfocusedLabelColor = MoviesWatchProTheme.colors.textSecondary,
                cursorColor = MoviesWatchProTheme.colors.textSecondary,
                errorCursorColor = MoviesWatchProTheme.colors.textSecondary,
                errorLabelColor = MoviesWatchProTheme.colors.error,
                focusedTrailingIconColor = MoviesWatchProTheme.colors.textSecondary,
                unfocusedTrailingIconColor = MoviesWatchProTheme.colors.textSecondary,

            ),
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(text = "Enter password") },
            isError = isError,
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
        )

        if (isError) {
            Text(
                text = "Error: Invalid email",
                color = MoviesWatchProTheme.colors.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }

        BottomViews()

        Spacer(modifier = Modifier.size(spacing))

        Column(modifier = Modifier.fillMaxWidth()) {
            MoviesWatchButton(
                shape = RoundedCornerShape(size = 12.dp),
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
//                    splashViewModel.setUserLoggedIn()
                    navController.navigateToRoute(
                        route = MainDestinations.HOME_ROUTE,
                        noTrace = true
                    )
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Sign In",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center) // This centers the text both vertically and horizontally
                    )
                }
//                Text(text = "Sign In", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
            }

            Spacer(modifier = Modifier.size(spacing))

            MoviesWatchButton(
                backgroundGradient = listOf(Color.White, Color.White),
                border = BorderStroke(1.dp, MoviesWatchProTheme.colors.textInteractive),
                onClick = {
//                googleSignIn(splashViewModel, scope, context, navController)
                }, shape = RoundedCornerShape(size = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google_button),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sign in with Google", color = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.size(spacing))

        noAccountSignUp()
    }
}

fun googleSignIn(
    splashScreenViewModel: SplashScreenViewModel,
    scope: CoroutineScope,
    context: Context,
    navController: MoviesWatchNavController,
) {
    val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(ConstantUtils.WEB_KEY)
            .setNonce("")
            .build()
    val request: GetCredentialRequest =
        Builder()
            .addCredentialOption(googleIdOption)
            .build()
    val credentialManager = CredentialManager.create(context = context)
    scope.launch {
        try {
            val result =
                credentialManager.getCredential(
                    request = request,
                    context = context,
                )
            handleSignIn(splashScreenViewModel, result, navController)
        } catch (e: GetCredentialException) {
            handleFailure(e)
        }
    }
}

private fun handleSignIn(
    splashScreenViewModel: SplashScreenViewModel,
    result: GetCredentialResponse,
    navController: MoviesWatchNavController,
) {
    val credential = result.credential
    when (credential) {
        is PublicKeyCredential -> {
            // Share responseJson such as a GetCredentialResponse on your server to
        }

        is PasswordCredential -> {
            // Send ID and password to your server to validate and authenticate.
            val userName = credential.id
            val password = credential.password
        }

        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val token = googleIdTokenCredential.idToken
                    // Send token to server for validation
                    navController.navigateToRoute(route = MainDestinations.HOME_ROUTE)
                    splashScreenViewModel.setUserLoggedIn()
                } catch (e: GoogleIdTokenParsingException) {
                    Timber.tag("Google id token error").i(e.message)
                }
            }
        }
    }
}

private fun handleFailure(e: GetCredentialException) {
    Timber.tag("Google Sign In Error").i(e.message)
}

@Composable
fun BottomViews() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.wrapContentSize()) {
            Checkbox(
                colors = CheckboxColors(
                    checkedBorderColor = MoviesWatchProTheme.colors.brand,
                    uncheckedBorderColor = MoviesWatchProTheme.colors.brand,
                    checkedCheckmarkColor = MoviesWatchProTheme.colors.brand,
                    uncheckedCheckmarkColor = MoviesWatchProTheme.colors.brand,
                    checkedBoxColor = MoviesWatchProTheme.colors.brand,
                    uncheckedBoxColor = MoviesWatchProTheme.colors.brand,
                    disabledCheckedBoxColor = MoviesWatchProTheme.colors.brand,
                    disabledUncheckedBoxColor = MoviesWatchProTheme.colors.brand,
                    disabledBorderColor = MoviesWatchProTheme.colors.brand,
                    disabledUncheckedBorderColor = MoviesWatchProTheme.colors.brand,
                    disabledIndeterminateBorderColor = MoviesWatchProTheme.colors.brand,
                    disabledIndeterminateBoxColor = MoviesWatchProTheme.colors.brand,
                ),
                checked = isChecked,
                onCheckedChange = { isChecked = !isChecked }
            )
        }

        Box(
            modifier =
            Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
                .weight(1f),
        ) {
            Text(
                text = "Remember for 30 days",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(end = 0.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            contentAlignment = Alignment.CenterEnd,
        ) {
            TextButton(onClick = { forgotPassword() }) {
                Text(text = "Forgot Password", color = MoviesWatchProTheme.colors.brand)
            }
        }
    }
}

fun forgotPassword() {
//    TODO("Not yet implemented")
}

@Composable
fun noAccountSignUp() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = "Don't have an account?")
        TextButton(onClick = { }) {
            Text(text = "Sign Up", color = MoviesWatchProTheme.colors.brand)
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    MoviesWatchProTheme {
        SignUpScreen(navController = rememberMoviesWatchNavController())
    }
}
