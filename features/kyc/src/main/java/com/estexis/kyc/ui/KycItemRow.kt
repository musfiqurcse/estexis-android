package com.estexis.kyc.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun KycItemRow(
    @DrawableRes image: Int,
    title: String,
    status: KycStatus,
    onClick: () -> Unit,
    hasBorder: Boolean = true
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = dimensions.spaces.x6, horizontal = dimensions.spaces.x6),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(dimensions.sizes.x9),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensions.spaces.x3),
            ) {
                Text(
                    text = title,
                    style = AppTextStyles.BodyText1Regular,
                    color = colors.tertiary,
                )

                VerticalSpacer(dimensions.spaces.x1)

                Text(
                    text = statusLabel(status),
                    style = AppTextStyles.BodyText3Bold,
                    color = statusColor(status),
                )
            }

            StatusBadge(status)
        }

        if (hasBorder) {
            HorizontalDivider(color = colors.border)
        }
    }
}

@Composable
private fun StatusBadge(status: KycStatus) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val (icon, tint) = when (status) {
        KycStatus.VERIFIED -> Icons.Default.CheckCircle to colors.success
        KycStatus.FAILED -> Icons.Default.Cancel to colors.error
        KycStatus.NOT_VERIFIED -> Icons.Default.Warning to colors.primary
        KycStatus.PENDING -> Icons.Default.HourglassEmpty to colors.action
        KycStatus.UNDER_REVIEW -> Icons.Default.HourglassEmpty to colors.action
    }

    Box(
        modifier = Modifier
            .size(dimensions.sizes.x7)
            .clip(CircleShape)
            .background(tint),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(dimensions.sizes.x5),
        )
    }
}

@Composable
private fun statusColor(status: KycStatus): Color = when (status) {
    KycStatus.VERIFIED -> AppTheme.colors.success
    KycStatus.FAILED -> AppTheme.colors.error
    KycStatus.NOT_VERIFIED -> AppTheme.colors.primary
    KycStatus.PENDING -> AppTheme.colors.action
    KycStatus.UNDER_REVIEW -> AppTheme.colors.action
}

private fun statusLabel(status: KycStatus): String = when (status) {
    KycStatus.VERIFIED -> "Verified"
    KycStatus.FAILED -> "Verified Failed"
    KycStatus.NOT_VERIFIED -> "Not Verified"
    KycStatus.PENDING -> "Pending"
    KycStatus.UNDER_REVIEW -> "Under Review"
}

@Preview(showBackground = true)
@Composable
private fun KycItemRowPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(AppTheme.dimensions.spaces.x4)) {
            KycItemRow(
                image = AppIcon.NidVerification.resId,
                title = "NID Verification",
                status = KycStatus.VERIFIED,
                onClick = {},
            )
            KycItemRow(
                image = AppIcon.Passport.resId,
                title = "Passport",
                status = KycStatus.PENDING,
                onClick = {},
            )
            KycItemRow(
                image = AppIcon.License.resId,
                title = "Driving License",
                status = KycStatus.UNDER_REVIEW,
                onClick = {},
            )
            KycItemRow(
                image = AppIcon.NidVerification.resId,
                title = "NID Verification",
                status = KycStatus.FAILED,
                onClick = {},
            )
            KycItemRow(
                image = AppIcon.Passport.resId,
                title = "Passport",
                status = KycStatus.NOT_VERIFIED,
                onClick = {},
                hasBorder = false,
            )
        }
    }
}
