package com.muthiani.movieswatchpro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muthiani.movieswatchpro.ui.intro.OnboardingScreen
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }

    @Composable
    private fun MainScreen() {
        val isSplashShow by splashViewModel.isSplashShow.collectAsState()
        MoviesWatchProTheme {
            Surface(color = MaterialTheme.colorScheme.surface) {
                if (isSplashShow) {
                    ShowOnBoardingScreen()
                } else {
                    ShowHomeScreen()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }

    @Composable
    private fun ShowHomeScreen() {
        HomeScreen()
    }

    @Composable
    private fun ShowOnBoardingScreen() {
        val context = LocalContext.current
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            OnboardingScreen {
                splashViewModel.setOnBoardingComplete()
                Toast.makeText(context, "Onboarding completed", Toast.LENGTH_SHORT).show()
            }
        }
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



