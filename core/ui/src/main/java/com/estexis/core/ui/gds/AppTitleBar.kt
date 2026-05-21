package com.estexis.core.ui.gds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun AppTitleBar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    title: String? = null,
    rightIcon: ImageVector? = null,
    onRightIconClick: (() -> Unit)? = null,
    contentDescriptionBackIcon: String? = "Back",
    contentDescriptionRightIcon: String? = null
) {

    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensions.spaces.x2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackClick != null) {
            Box(
                modifier = modifier
                    .size(dimensions.sizes.x10)
                    .clip(CircleShape)
                    .background(AppTheme.colors.background)
                    .clickable {
                        onBackClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(AppIcon.ArrowBackward.resId),
                    contentDescription = contentDescriptionBackIcon,
                    colorFilter = ColorFilter.tint(colors.tertiary),
                    modifier = Modifier.size(dimensions.sizes.x8),
                )
            }
        }

        if (title != null) {
            HorizontalSpacer(dimensions.spaces.x1)
            Text(
                text = title,
                style = AppTheme.typography.BodyText1Bold,
                color = colors.tertiary,
                modifier = Modifier.padding(start = dimensions.spaces.x2),
            )
        } else {
            Spacer(Modifier.weight(1f))
        }

        if (rightIcon != null && onRightIconClick != null) {
            Icon(
                imageVector = rightIcon,
                contentDescription = contentDescriptionRightIcon,
                tint = colors.onBackground,
                modifier = Modifier
                    .size(dimensions.sizes.x7)
                    .clickable(onClick = onRightIconClick),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun AppTitleBarBackAndTitlePreview() {
    ExtexisAndroidTheme {
        AppTitleBar(
            onBackClick = {},
            title = "Profile",
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun AppTitleBarFullPreview() {
    ExtexisAndroidTheme {
        AppTitleBar(
            onBackClick = {},
            title = "Account Information",
            rightIcon = Icons.Default.Edit,
            onRightIconClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun AppTitleBarBackAndRightIconPreview() {
    ExtexisAndroidTheme {
        AppTitleBar(
            onBackClick = {},
            rightIcon = Icons.Default.Edit,
            onRightIconClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun AppTitleBarBackOnlyPreview() {
    ExtexisAndroidTheme {
        AppTitleBar(onBackClick = {})
    }
}
