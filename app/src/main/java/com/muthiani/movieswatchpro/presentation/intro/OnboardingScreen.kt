package com.muthiani.movieswatchpro.presentation.intro

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muthiani.movieswatchpro.domain.entity.IntroModel
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchButton
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages = listOf(IntroModel.FirstIntro, IntroModel.SecondIntro, IntroModel.ThirdIntro)

    val pagerState =
        rememberPagerState(initialPage = 0) {
            pages.size
        }

    val buttonState =
        remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Start")
                    else -> listOf("", "")
                }
            }
        }

    val scope = rememberCoroutineScope()

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier,
            containerColor = MoviesWatchProTheme.colors.uiBackground,
            bottomBar = {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (buttonState.value[0].isNotEmpty()) {
                            MoviesWatchButton(
                                onClick = {
                                    scope.launch {
                                        if (pagerState.currentPage > 0) {
                                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                        }
                                    }
                                },
                            ) {
                                Text(text = buttonState.value[0])
                            }
                        }
                    }

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        IndicatorUi(pageSize = pages.size, currentPage = pagerState.currentPage)
                    }

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        MoviesWatchButton(
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage < pages.size - 1) {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                    } else {
                                        onFinished()
                                    }
                                }
                            },
                        ) {
                            Text(text = buttonState.value[1])
                        }
                    }
                }
            },
            content = {
                Column(Modifier.padding(it)) {
                    HorizontalPager(state = pagerState) { index ->
                        IntroScreen(introModel = pages[index])
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun OnboardingPreview() {
    MoviesWatchProTheme {
        OnboardingScreen(onFinished = {})
    }
}
