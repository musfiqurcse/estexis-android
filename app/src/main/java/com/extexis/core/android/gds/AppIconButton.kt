package com.extexis.core.android.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

@Composable
fun AppIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    containerColor: Color = AppTheme.colors.background,
    iconTint: Color = AppTheme.colors.onBackground,
) {
    val dimensions = AppTheme.dimensions

    Box(
        modifier = modifier
            .size(dimensions.sizes.x10)
            .clip(CircleShape)
            .background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(dimensions.sizes.x5),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun AppIconButtonPreview() {
    ExtexisAndroidTheme {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spaces.x4),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spaces.x3),
        ) {
            AppIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {},
                contentDescription = "Back",
            )
            AppIconButton(
                icon = Icons.Default.Favorite,
                onClick = {},
                iconTint = AppTheme.colors.error,
            )
        }
    }
}
