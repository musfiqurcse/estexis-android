package com.extexis.registration.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.extexis.core.ui.gds.VerticalSpacer
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.registration.domain.AccountRole

@Composable
fun RoleSelectorView(
    selected: AccountRole?,
    isError: Boolean,
    errorMessage: String?,
    onRoleSelected: (AccountRole) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column {
        Text(
            text = buildAnnotatedString {
                append("Role")
                append(" ")
                withStyle(SpanStyle(color = colors.error)) { append("*") }
            },
            style = AppTextStyles.BodyText3Bold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x1)

        Row(verticalAlignment = Alignment.CenterVertically) {
            AccountRole.entries.forEach { role ->
                RadioButton(
                    selected = selected == role,
                    onClick = { onRoleSelected(role) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colors.primary,
                        unselectedColor = if (isError) colors.error else colors.border,
                    ),
                )
                Text(
                    text = role.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = AppTextStyles.BodyText1Regular,
                    color = colors.onBackground,
                )
                Spacer(Modifier.width(16.dp))
            }
        }

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
