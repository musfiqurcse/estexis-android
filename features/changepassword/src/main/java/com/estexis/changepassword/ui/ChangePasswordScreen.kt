package com.estexis.changepassword.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.changepassword.R
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.AppLoadingDialog
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChangePasswordScreen(
    state: ChangePasswordState,
    event: (ChangePasswordUiEvent) -> Unit,
) {
    if (state.isLoading) AppLoadingDialog()

    if (state.isSuccess) {
        ChangePasswordSuccessScreen(event = event)
    } else {
        ChangePasswordFormScreen(state = state, event = event)
    }
}

@Composable
private fun ChangePasswordFormScreen(
    state: ChangePasswordState,
    event: (ChangePasswordUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addBackground()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensions.spaces.x4),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(ChangePasswordUiEvent.BackClicked) },
                title = stringResource(R.string.change_password_screen_title),
            )

            VerticalSpacer(dimensions.spaces.x8)

            AppTextField(
                value = state.currentPassword,
                onValueChange = { event(ChangePasswordUiEvent.CurrentPasswordChanged(it)) },
                placeholder = stringResource(R.string.change_password_screen_placeholder_old_password),
                leadingIcon = AppIcon.LockPassword.resId,
                isPassword = true,
                isError = state.currentPasswordError != null,
                errorMessage = state.currentPasswordError?.asString(),
            )

            VerticalSpacer(dimensions.spaces.x3)

            AppTextField(
                value = state.newPassword,
                onValueChange = { event(ChangePasswordUiEvent.NewPasswordChanged(it)) },
                placeholder = stringResource(R.string.change_password_screen_placeholder_new_password),
                leadingIcon = AppIcon.LockPassword.resId,
                isPassword = true,
                isError = state.newPasswordError != null,
                errorMessage = state.newPasswordError?.asString(),
            )

            VerticalSpacer(dimensions.spaces.x3)

            AppTextField(
                value = state.confirmPassword,
                onValueChange = { event(ChangePasswordUiEvent.ConfirmPasswordChanged(it)) },
                placeholder = stringResource(R.string.change_password_screen_placeholder_confirm_password),
                leadingIcon = AppIcon.LockPassword.resId,
                isPassword = true,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError?.asString(),
            )

            Spacer(Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
        ) {
            AppButton(
                text = stringResource(R.string.change_password_screen_cta_change),
                onClick = { event(ChangePasswordUiEvent.SaveClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
            )
        }
    }
}

@Composable
private fun ChangePasswordSuccessScreen(
    event: (ChangePasswordUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions
    val colors = AppTheme.colors
    val badgeColor = Color(0xFFD95A2B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addBackground(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = dimensions.spaces.x4)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(ChangePasswordUiEvent.BackClicked) },
            )

            Spacer(Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp),
            ) {
                RosetteBadge(
                    color = badgeColor,
                    modifier = Modifier.fillMaxSize(),
                )
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp),
                )
            }

            VerticalSpacer(dimensions.spaces.x6)

            Text(
                text = stringResource(R.string.change_password_success_title),
                style = AppTextStyles.H2SemiBold,
                color = badgeColor,
                textAlign = TextAlign.Center,
            )

            VerticalSpacer(dimensions.spaces.x3)

            Text(
                text = stringResource(R.string.change_password_success_message),
                style = AppTextStyles.BodyText2Regular,
                color = colors.tertiary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
        ) {
            AppButton(
                text = stringResource(R.string.change_password_screen_cta_continue),
                onClick = { event(ChangePasswordUiEvent.ContinueClicked) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun RosetteBadge(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val outerR = size.minDimension / 2f
        val innerR = outerR * 0.78f
        val petals = 16
        val path = Path()

        for (i in 0 until petals) {
            val angle = (i * 360.0 / petals) * (Math.PI / 180.0)
            val nextAngle = ((i + 0.5) * 360.0 / petals) * (Math.PI / 180.0)
            val px = cx + outerR * cos(angle).toFloat()
            val py = cy + outerR * sin(angle).toFloat()
            val mx = cx + innerR * cos(nextAngle).toFloat()
            val my = cy + innerR * sin(nextAngle).toFloat()

            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
            path.lineTo(mx, my)
        }
        path.close()
        drawPath(path, color)
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangePasswordFormPreview() {
    ExtexisAndroidTheme {
        ChangePasswordScreen(state = ChangePasswordState(), event = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangePasswordSuccessPreview() {
    ExtexisAndroidTheme {
        ChangePasswordScreen(state = ChangePasswordState(isSuccess = true), event = {})
    }
}
