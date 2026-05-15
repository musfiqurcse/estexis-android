package com.extexis.core.ui.gds

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.ui.theme.ExtexisAndroidTheme

private const val ALPHA_VALUE = 0.38f

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AppButtonVariant = AppButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = AppTheme.colors
    val shape = RoundedCornerShape(AppTheme.dimensions.radius.pill)

    when (variant) {
        AppButtonVariant.OUTLINED -> {
            val contentColor =
                if (enabled) colors.action else colors.action.copy(alpha = ALPHA_VALUE)
            OutlinedButton(
                onClick = onClick,
                modifier = modifier.height(AppTheme.dimensions.sizes.x13),
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = contentColor,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colors.action.copy(alpha = ALPHA_VALUE),
                ),
                border = BorderStroke(
                    width = AppTheme.dimensions.borders.low,
                    color = if (enabled) colors.action else colors.action.copy(alpha = ALPHA_VALUE),
                ),
                contentPadding = PaddingValues(
                    horizontal = AppTheme.dimensions.spaces.x7,
                    vertical = AppTheme.dimensions.spaces.x4
                ),
            ) {
                ButtonContent(
                    text = text,
                    contentColor = contentColor,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                )
            }
        }

        else -> {
            val bgColor = when (variant) {
                AppButtonVariant.PRIMARY -> colors.secondary
                AppButtonVariant.SECONDARY -> colors.primaryContainer
            }
            val contentColor = when (variant) {
                AppButtonVariant.PRIMARY -> colors.white
                AppButtonVariant.SECONDARY -> colors.onBackground
            }
            Button(
                onClick = onClick,
                modifier = modifier.height(AppTheme.dimensions.sizes.x13),
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = bgColor,
                    contentColor = contentColor,
                    disabledContainerColor = bgColor.copy(alpha = ALPHA_VALUE),
                    disabledContentColor = contentColor.copy(alpha = ALPHA_VALUE),
                ),
                contentPadding = PaddingValues(
                    horizontal = AppTheme.dimensions.spaces.x7,
                    vertical = AppTheme.dimensions.spaces.x4
                ),
                elevation = ButtonDefaults.buttonElevation(
                    AppTheme.dimensions.elevations.zero,
                    AppTheme.dimensions.elevations.zero,
                    AppTheme.dimensions.elevations.zero
                ),
            ) {
                ButtonContent(
                    text = text,
                    contentColor = contentColor,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                )
            }
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    contentColor: Color,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = contentColor,
            )
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = text,
            style = AppTextStyles.ButtonLabel,
            color = contentColor,
        )

        if (trailingIcon != null) {
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = contentColor,
            )
        }
    }
}

enum class AppButtonVariant {
    PRIMARY,
    SECONDARY,
    OUTLINED,
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AppButtonPreview() {
    ExtexisAndroidTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppButton(
                text = "Login",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
            AppButton(
                text = "Continue",
                onClick = {},
            )
            AppButton(
                text = "Back",
                onClick = {},
                variant = AppButtonVariant.SECONDARY,
            )
            AppButton(
                text = "Re-Generate",
                onClick = {},
                variant = AppButtonVariant.OUTLINED,
                leadingIcon = Icons.Default.AutoAwesome,
            )
            AppButton(
                text = "Login",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )
        }
    }
}
