package com.muthiani.movieswatchpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiTypeHolder: ApiLoadTypeHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesWatchProTheme {
                MoviesWatchApp(apiTypeHolder)
            }
        }
    }
}
