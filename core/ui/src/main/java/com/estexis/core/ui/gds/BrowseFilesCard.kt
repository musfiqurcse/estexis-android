package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.clipRounded

@Composable
fun BrowseFilesCard(
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonLabel: String = "Browse Files",
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipRounded(dimensions.radius.large)
            .height(dimensions.sizes.x36)
            .background(colors.white)
            .clickable(onClick = onBrowseClick)
            .dashedBorder(
                color = colors.secondary,
                cornerRadius = dimensions.radius.large,
            )
            .padding(dimensions.spaces.x4),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(dimensions.sizes.x10),
            )

            Box(
                modifier = Modifier
                    .padding(top = dimensions.spaces.x4)
                    .clip(RoundedCornerShape(dimensions.radius.medium))
                    .background(colors.onPrimary),
            ) {
                Text(
                    text = buttonLabel,
                    style = AppTextStyles.BodyText3SemiBold,
                    color = colors.action,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensions.radius.medium))
                        .background(colors.onPrimary)
                        .padding(
                            horizontal = dimensions.spaces.x6,
                            vertical = dimensions.spaces.x4,
                        ),
                )
            }
        }
    }
}

private fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
): Modifier = this.drawBehind {
    drawRoundRect(
        color = color,
        style = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(16f, 12f),
                phase = 0f,
            ),
        ),
        cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun BrowseFilesCardPreview() {
    ExtexisAndroidTheme {
        Box(modifier = Modifier.padding(AppTheme.dimensions.spaces.x4)) {
            BrowseFilesCard(onBrowseClick = {})
        }
    }
}
