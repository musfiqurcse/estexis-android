package com.estexis.kyc.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground

@Composable
private fun shimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.4f),
        Color.LightGray.copy(alpha = 0.1f),
        Color.LightGray.copy(alpha = 0.4f),
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_translate",
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200f, 0f),
        end = Offset(translateAnim, 0f),
    )
}

@Composable
private fun ShimmerBox(
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp,
    shape: Shape = RoundedCornerShape(AppTheme.dimensions.radius.medium),
) {
    val brush = shimmerBrush()
    Box(
        modifier = modifier
            .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
            .height(height)
            .clip(shape)
            .background(brush),
    )
}

@Composable
private fun ShimmerItemRow(hasBorder: Boolean = true) {
    val dimensions = AppTheme.dimensions

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (hasBorder) Modifier.padding(bottom = dimensions.spaces.x3) else Modifier)
            .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShimmerBox(
            width = dimensions.sizes.x12,
            height = dimensions.sizes.x12,
            shape = CircleShape,
        )
        Spacer(modifier = Modifier.width(dimensions.spaces.x3))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x2),
        ) {
            ShimmerBox(width = dimensions.sizes.x35, height = dimensions.sizes.x4)
            ShimmerBox(width = dimensions.sizes.x20, height = dimensions.sizes.x3)
        }
        ShimmerBox(
            width = dimensions.sizes.x18,
            height = dimensions.sizes.x6,
            shape = RoundedCornerShape(dimensions.radius.pill),
        )
    }
}

@Composable
private fun ShimmerSection(itemCount: Int) {
    val dimensions = AppTheme.dimensions
    val colors = AppTheme.colors

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensions.spaces.x3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerBox(width = dimensions.sizes.x40, height = dimensions.sizes.x5)
            ShimmerBox(width = dimensions.sizes.x6, height = dimensions.sizes.x6, shape = CircleShape)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensions.radius.large))
                .background(colors.white),
        ) {
            repeat(itemCount) { index ->
                ShimmerItemRow(hasBorder = index < itemCount - 1)
            }
        }
    }
}

@Composable
fun KycShimmer() {
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.addBackground()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.spaces.x4),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerBox(width = dimensions.sizes.x6, height = dimensions.sizes.x6, shape = CircleShape)
            Spacer(modifier = Modifier.width(dimensions.spaces.x4))
            ShimmerBox(width = dimensions.sizes.x25, height = dimensions.sizes.x5)
        }

        Column(modifier = Modifier.padding(horizontal = dimensions.spaces.x4)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = dimensions.spaces.x4),
            ) {
                ShimmerBox(
                    width = dimensions.sizes.x12,
                    height = dimensions.sizes.x12,
                    shape = CircleShape,
                )
                Spacer(modifier = Modifier.width(dimensions.spaces.x3))
                ShimmerBox(width = dimensions.sizes.x41, height = dimensions.sizes.x5)
            }

            ShimmerSection(itemCount = 3)

            Spacer(modifier = Modifier.height(dimensions.spaces.x4))

            ShimmerSection(itemCount = 2)
        }
    }
}

@Composable
fun KycRetry(
    message: String,
    onRetry: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addBackground()
            .padding(dimensions.spaces.x4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = message,
            style = AppTextStyles.BodyText1Regular,
            color = colors.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(dimensions.spaces.x4))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KycShimmerPreview() {
    ExtexisAndroidTheme {
        KycShimmer()
    }
}
