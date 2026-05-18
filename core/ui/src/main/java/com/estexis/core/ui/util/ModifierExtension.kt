package com.estexis.core.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.estexis.core.ui.theme.AppTheme

@Composable
fun Modifier.clipRounded(radius: Dp = AppTheme.dimensions.radius.medium) =
    this.clip(RoundedCornerShape(radius))

@Composable
fun Modifier.clipCircular() = this.clip(CircleShape)

@Composable
fun roundedShape(radius: Dp = AppTheme.dimensions.radius.medium) = RoundedCornerShape(radius)

@Composable
fun Modifier.addBorder(color: Color) = this.border(
    border = BorderStroke(
        color = color,
        width = AppTheme.dimensions.borders.veryLow
    ),
    shape = RoundedCornerShape(AppTheme.dimensions.radius.medium)
)

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick()
    }
}

@Composable
fun Modifier.addStatusBarPadding() = this.padding(WindowInsets.statusBars.asPaddingValues())

@Composable
fun Modifier.addNavigationBarPadding() = this.padding(WindowInsets.navigationBars.asPaddingValues())

@Composable
fun Modifier.addSystemBarPadding() = this.padding(WindowInsets.systemBars.asPaddingValues())
