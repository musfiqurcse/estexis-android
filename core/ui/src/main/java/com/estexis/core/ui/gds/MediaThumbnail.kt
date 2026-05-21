package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun MediaThumbnail(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimensions.radius.large))
            .background(colors.surface),
    ) {
        content()

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(dimensions.spaces.x1)
                .size(dimensions.sizes.x6)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f)),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = colors.white,
                modifier = Modifier.size(dimensions.sizes.x4),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaThumbnailPreview() {
    ExtexisAndroidTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MediaThumbnail(
                onDelete = {},
                modifier = Modifier.size(120.dp),
            ) {
                Box(modifier = Modifier.size(120.dp).background(Color.Gray))
            }
        }
    }
}
