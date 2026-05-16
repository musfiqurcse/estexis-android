package com.extexis.registration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme

@Composable
fun CountryPickerFieldView(
    selected: String?,
    isError: Boolean,
    errorMessage: String?,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Text(
            text = buildAnnotatedString {
                append("Country")
                append(" ")
                withStyle(SpanStyle(color = colors.error)) { append("*") }
            },
            style = AppTextStyles.BodyMedium,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x1))

        OutlinedTextField(
            value = selected ?: "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true,
            placeholder = {
                Text(
                    text = "Select your country",
                    style = AppTextStyles.Body,
                    color = colors.surfaceDim,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(dimensions.sizes.x5),
                    tint = colors.surfaceDim,
                )
            },
            isError = isError,
            shape = RoundedCornerShape(dimensions.radius.xlarge),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = colors.background,
                disabledBorderColor = if (isError) colors.error else Color.Transparent,
                disabledTextColor = colors.onBackground,
                disabledPlaceholderColor = colors.surfaceDim,
                disabledTrailingIconColor = colors.surfaceDim,
            ),
        )

        if (isError && errorMessage != null) {
            Spacer(Modifier.height(dimensions.spaces.x1))
            Text(
                text = errorMessage,
                style = AppTextStyles.Meta,
                color = colors.error,
                modifier = Modifier.padding(horizontal = dimensions.spaces.x1),
            )
        }
    }
}
