package com.muthiani.movieswatchpro.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muthiani.movieswatchpro.models.IntroModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun introScreen(introModel: IntroModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(80.dp))

        Image(
            painter = painterResource(id = introModel.image),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
            alignment = Alignment.Center,
        )

        Spacer(modifier = Modifier.size(70.dp))

        Text(
            text = introModel.title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MoviesWatchProTheme.colors.textInteractive,
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = introModel.description,
            modifier = Modifier.fillMaxWidth().padding(15.dp, 0.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MoviesWatchProTheme.colors.textInteractive,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun introScreenPreview1() {
    introScreen(introModel = IntroModel.FirstIntro)
}

@Preview(showBackground = true)
@Composable
fun introScreenPreview2() {
    introScreen(introModel = IntroModel.SecondIntro)
}

@Preview(showBackground = true)
@Composable
fun introScreenPreview3() {
    introScreen(introModel = IntroModel.ThirdIntro)
}
