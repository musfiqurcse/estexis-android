package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun ToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    leading: @Composable (() -> Unit)? = null,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensions.radius.pill))
            .background(colors.background)
            .padding(
                horizontal = dimensions.spaces.x4,
                vertical = dimensions.spaces.x3,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leading != null) {
            leading()
            Spacer(Modifier.width(dimensions.spaces.x3))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTextStyles.BodyText1Bold,
                color = colors.onBackground,
            )
            Text(
                text = subtitle,
                style = AppTextStyles.BodyText3Regular,
                color = colors.tertiary,
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.white,
                checkedTrackColor = colors.secondary,
                uncheckedThumbColor = colors.white,
                uncheckedTrackColor = colors.surfaceDim,
                uncheckedBorderColor = Color.Transparent,
            ),
        )
    }
}

@Composable
fun ToggleRowLeadingCircle(
    color: Color,
    letter: String,
) {
    Box(
        modifier = Modifier
            .size(AppTheme.dimensions.sizes.x10)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter,
            style = AppTextStyles.BodyText1Bold,
            color = AppTheme.colors.white,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ToggleRowPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ToggleRow(
                title = "Instagram",
                subtitle = "Feed & Stories",
                checked = true,
                onCheckedChange = {},
                leading = { ToggleRowLeadingCircle(Color(0xFFE1306C), "I") },
            )
        }
    }
}
