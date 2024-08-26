package com.muthiani.movieswatchpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.muthiani.movieswatchpro.ui.intro.OnboardingScreen
import com.muthiani.movieswatchpro.ui.signup.SignUpScreen
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.utils.ConstantUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashScreenViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesWatchProTheme {
                MoviesWatch()
            }
        }
    }

    @Composable
    fun MoviesWatch() {
        val isOnboarded = splashViewModel.isUserOnboarded
        val isLoggedIn = splashViewModel.isLoggedIn
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()

        NavHost(
            navController = navController, startDestination = when (isOnboarded) {
                true -> {
                    if (isLoggedIn) "home" else "login"
                }

                else -> "onboarding"
            }
        ) {
            composable(route = "login",  exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }) {
                SignUpScreen(
                    onGoogleSignIn = {

                        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(true)
                            .setServerClientId(ConstantUtils.WEB_KEY)
                            .setAutoSelectEnabled(true)
                            .setNonce("")
                            .build()
                        val request: GetCredentialRequest = Builder()
                            .addCredentialOption(googleIdOption)
                            .build()
                        val credentialManager = CredentialManager.create(context = this@MainActivity.baseContext)
                        scope.launch {
                            try {
                                val result = credentialManager.getCredential(request = request,
                                    context = this@MainActivity.baseContext)
                                handleSignIn(result)

                            }catch (e: GetCredentialException) {
                                handleFailure(e)
                            }
                        }

                    },
                    onNavigateHome = {
                        navController.navigate(route = "home")
                    }
                )
            }
            composable(route = "onboarding",
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }) {
                OnboardingScreen(
                    onFinished = {
                        splashViewModel.setOnBoardingComplete()
                        navController.navigate(route = "login")
                    }
                )
            }
            composable(route = "home",  exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }) {
                HomeScreen()
            }
        }
    }

    private fun handleFailure(e: GetCredentialException) {

    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        when(credential) {
            is PublicKeyCredential -> {

            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MoviesWatch()
    }

    @Composable
    fun HomeScreen() {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp) // Add padding for status bar
                    .fillMaxSize()
            ) {
                Text(text = "Home Screen", style = MaterialTheme.typography.titleLarge)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        HomeScreen()
    }
}



