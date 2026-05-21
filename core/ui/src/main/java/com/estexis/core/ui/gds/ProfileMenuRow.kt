package com.estexis.core.ui.gds

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun ProfileMenuRow(
    @DrawableRes image: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.sizes.x13)
            .clip(RoundedCornerShape(dimensions.radius.pill))
            .background(colors.white)
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensions.spaces.x4
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(colors.secondary),
            modifier = modifier.size(dimensions.sizes.x6)
        )

        HorizontalSpacer(dimensions.spaces.x3)

        Text(
            text = label,
            style = AppTextStyles.BodyText2Regular,
            color = colors.onBackground,
            modifier = Modifier.weight(1f),
        )

        Icon(
            painter = painterResource(AppIcon.ArrowForward.resId),
            contentDescription = null,
            tint = colors.tertiary,
            modifier = Modifier.size(dimensions.sizes.x5),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileMenuRowPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileMenuRow(
                image = AppIcon.UserAccount.resId,
                label = "Account Information",
                onClick = {},
            )
            Spacer(Modifier.height(8.dp))
            ProfileMenuRow(
                image = AppIcon.Language.resId,
                label = "Security",
                onClick = {},
            )
        }
    }
}
