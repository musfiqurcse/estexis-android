package com.estexis.core.ui.gds

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    isRequired: Boolean = false,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
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
                style = AppTextStyles.BodyText3Bold,
                color = colors.tertiary,
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
                    style = AppTextStyles.BodyText2Regular,
                    color = colors.onBackground,
                )
            },
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(dimensions.sizes.x5),
                        tint = colors.tertiary,
                    )
                }
            } else null,
            trailingIcon = when {
                isPassword -> {
                    {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible)
                                    painterResource(AppIcon.VisibilityOff.resId)
                                else
                                    painterResource(AppIcon.VisibilityOn.resId),
                                contentDescription = null,
                                modifier = Modifier.size(dimensions.sizes.x5),
                                tint = colors.tertiary,
                            )
                        }
                    }
                }
                trailingIcon != null -> {
                    {
                        if (onTrailingIconClick != null) {
                            IconButton(onClick = onTrailingIconClick) {
                                Icon(
                                    painter = painterResource(trailingIcon),
                                    contentDescription = null,
                                    modifier = Modifier.size(dimensions.sizes.x5),
                                    tint = colors.tertiary,
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(trailingIcon),
                                contentDescription = null,
                                modifier = Modifier.size(dimensions.sizes.x5),
                                tint = colors.tertiary,
                            )
                        }
                    }
                }
                else -> null
            },
            isError = isError,
            shape = RoundedCornerShape(dimensions.radius.xlarge),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.surface,
                focusedBorderColor = colors.secondary,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = colors.error,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground,
                disabledTextColor = colors.surfaceDim,
                cursorColor = colors.secondary,
                errorCursorColor = colors.error,
                errorTextColor = colors.onBackground,
                errorLeadingIconColor = colors.surfaceDim,
                errorTrailingIconColor = colors.error,
                errorContainerColor = colors.background
            ),
        )

        if (isError && errorMessage != null) {
            Spacer(Modifier.height(dimensions.spaces.x1))
            Text(
                text = errorMessage,
                style = AppTextStyles.BodyText2Regular,
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
            )
            Spacer(Modifier.height(12.dp))
            AppTextField(
                value = "••••••••••••••",
                onValueChange = {},
                placeholder = "Password",
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
