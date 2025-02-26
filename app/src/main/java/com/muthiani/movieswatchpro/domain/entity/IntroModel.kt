package com.muthiani.movieswatchpro.domain.entity

import androidx.annotation.DrawableRes
import com.muthiani.movieswatchpro.R

sealed class IntroModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
    data object FirstIntro : IntroModel(
        image = R.drawable.first_intro,
        title = "Lorem ipsum dolor sit amet.",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam gravida.",
    )

    data object SecondIntro : IntroModel(
        image = R.drawable.second_intro,
        title = "Aliquam vehicula arcu non tempus.",
        description = "Sed ac aliquet mi. Donec condimentum, quam at pulvinar luctus.",
    )

    data object ThirdIntro : IntroModel(
        image = R.drawable.third_intro,
        title = "Aenean hendrerit dui quis efficitur.",
        description = "Praesent consequat quam sed nisi facilisis vestibulum. Curabitur eget magna.",
    )
}
