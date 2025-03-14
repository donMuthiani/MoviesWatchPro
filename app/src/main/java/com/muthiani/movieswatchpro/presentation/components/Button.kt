package com.muthiani.movieswatchpro.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun MoviesWatchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonShape,
    border: BorderStroke? = null,
    backgroundGradient: List<Color> = MoviesWatchProTheme.colors.interactivePrimary,
    disabledBackgroundGradient: List<Color> = MoviesWatchProTheme.colors.interactiveSecondary,
    contentColor: Color = MoviesWatchProTheme.colors.textInteractive,
    disabledContentColor: Color = MoviesWatchProTheme.colors.textHelp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    MoviesWatchSurface(
        shape = shape,
        color = Color.Transparent,
        contentColor = if (enabled) contentColor else disabledContentColor,
        border = border,
        modifier =
            modifier
                .clip(shape)
                .background(
                    Brush.horizontalGradient(
                        colors = if (enabled) backgroundGradient else disabledBackgroundGradient,
                    ),
                )
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                ),
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center),
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight,
                    )
                    .indication(interactionSource, rememberRipple())
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

private val ButtonShape = RoundedCornerShape(percent = 50)

@Preview("default", "round")
@Preview("dark theme", "round", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun ButtonPreview() {
    MoviesWatchProTheme {
        MoviesWatchButton(onClick = {}) {
            Text(text = "Demo")
        }
    }
}

@Preview("default", "rectangle")
@Preview("dark theme", "rectangle", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "rectangle", fontScale = 2f)
@Composable
private fun RectangleButtonPreview() {
    MoviesWatchProTheme {
        MoviesWatchButton(
            onClick = {},
            shape = RectangleShape,
        ) {
            Text(text = "Demo")
        }
    }
}
