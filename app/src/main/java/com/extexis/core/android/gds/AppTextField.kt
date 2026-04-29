package com.extexis.core.android.gds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.extexis.core.android.ui.theme.AppTextStyles
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    isRequired: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = when {
        isPassword && !passwordVisible -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    val resolvedTrailingIcon: ImageVector? = when {
        isPassword -> if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
        else -> trailingIcon
    }

    val resolvedTrailingClick: (() -> Unit)? = when {
        isPassword -> ({ passwordVisible = !passwordVisible })
        else -> onTrailingIconClick
    }

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = buildAnnotatedString {
                    append(label)
                    if (isRequired) {
                        append(" ")
                        withStyle(SpanStyle(color = colors.error)) { append("*") }
                    }
                },
                style = AppTextStyles.BodyMedium,
                color = colors.onBackground,
            )
            Spacer(Modifier.height(dimensions.spaces.x1))
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = if (isPassword) {
                keyboardOptions.copy(keyboardType = KeyboardType.Password)
            } else {
                keyboardOptions
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = AppTextStyles.Body,
                    color = colors.surfaceDim,
                )
            },
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(dimensions.sizes.x5),
                        tint = colors.surfaceDim,
                    )
                }
            } else null,
            trailingIcon = if (resolvedTrailingIcon != null) {
                {
                    if (resolvedTrailingClick != null) {
                        IconButton(onClick = resolvedTrailingClick) {
                            Icon(
                                imageVector = resolvedTrailingIcon,
                                contentDescription = null,
                                modifier = Modifier.size(dimensions.sizes.x5),
                                tint = colors.surfaceDim,
                            )
                        }
                    } else {
                        Icon(
                            imageVector = resolvedTrailingIcon,
                            contentDescription = null,
                            modifier = Modifier.size(dimensions.sizes.x5),
                            tint = colors.surfaceDim,
                        )
                    }
                }
            } else null,
            isError = isError,
            shape = RoundedCornerShape(dimensions.radius.xlarge),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.surface,
                focusedBorderColor = colors.secondary,
                unfocusedBorderColor = colors.border,
                errorBorderColor = colors.error,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground,
                disabledTextColor = colors.surfaceDim,
                cursorColor = colors.secondary,
                errorCursorColor = colors.error,
                errorTextColor = colors.onBackground,
                errorLeadingIconColor = colors.surfaceDim,
                errorTrailingIconColor = colors.error,
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

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun AppTextFieldPreview() {
    ExtexisAndroidTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = "Email address",
                leadingIcon = Icons.Default.Email,
            )
            Spacer(Modifier.height(12.dp))
            AppTextField(
                value = "••••••••••••••",
                onValueChange = {},
                placeholder = "Password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
            )
            Spacer(Modifier.height(12.dp))
            AppTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                isRequired = true,
                placeholder = "Enter your email",
            )
            Spacer(Modifier.height(12.dp))
            AppTextField(
                value = "",
                onValueChange = {},
                label = "First name",
                isRequired = true,
                placeholder = "Enter your first name",
                isError = true,
                errorMessage = "This field is required",
            )
        }
    }
}
