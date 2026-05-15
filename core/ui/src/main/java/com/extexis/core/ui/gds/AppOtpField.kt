package com.extexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme

private const val OTP_LENGTH = 6

@Composable
fun AppOtpField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    BasicTextField(
        value = value,
        onValueChange = { if (it.length <= OTP_LENGTH && it.all { c -> c.isDigit() }) onValueChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
            ) {
                repeat(OTP_LENGTH) { index ->
                    val char = value.getOrNull(index)
                    val isFocused = index == value.length

                    val borderColor = when {
                        isError -> colors.error
                        isFocused -> colors.secondary
                        char != null -> colors.secondary.copy(alpha = 0.5f)
                        else -> colors.border
                    }

                    Box(
                        modifier = Modifier
                            .size(width = dimensions.sizes.x12, height = dimensions.sizes.x14)
                            .background(colors.background, RoundedCornerShape(dimensions.radius.large))
                            .border(
                                width = if (isFocused) dimensions.borders.low else dimensions.borders.veryLow,
                                color = borderColor,
                                shape = RoundedCornerShape(dimensions.radius.xlarge),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = char?.toString() ?: "",
                            style = AppTextStyles.Title,
                            color = colors.onBackground,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    )
}
