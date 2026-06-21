package com.estexis.twofactorauth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppLoadingDialog
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground
import com.estexis.twofactorauth.R

@Composable
fun TwoFactorAuthScreen(
    state: TwoFactorAuthState,
    event: (TwoFactorAuthUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    if (state.isLoading) AppLoadingDialog()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addBackground(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensions.spaces.x4),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(TwoFactorAuthUiEvent.BackClicked) },
                title = stringResource(R.string.two_factor_auth_screen_title),
            )

            VerticalSpacer(dimensions.spaces.x6)

            Text(
                text = stringResource(R.string.two_factor_auth_screen_message),
                style = AppTextStyles.BodyText2Regular,
                color = colors.tertiary,
            )

            VerticalSpacer(dimensions.spaces.x8)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.two_factor_auth_screen_label_enabled),
                        style = AppTextStyles.BodyText2SemiBold,
                        color = colors.tertiary,
                    )
                    Text(
                        text = stringResource(
                            if (state.isEnabled) R.string.two_factor_auth_screen_status_enabled
                            else R.string.two_factor_auth_screen_status_disabled
                        ),
                        style = AppTextStyles.BodyText3Regular,
                        color = colors.surfaceDim,
                    )
                }
                Switch(
                    checked = state.isEnabled,
                    onCheckedChange = { event(TwoFactorAuthUiEvent.ToggleClicked) },
                    enabled = !state.isLoading,
                )
            }

            VerticalSpacer(dimensions.spaces.x8)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
        ) {
            AppButton(
                text = stringResource(
                    if (state.isEnabled) R.string.two_factor_auth_screen_cta_disable
                    else R.string.two_factor_auth_screen_cta_enable
                ),
                onClick = { event(TwoFactorAuthUiEvent.ToggleClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TwoFactorAuthScreenPreview() {
    ExtexisAndroidTheme {
        TwoFactorAuthScreen(
            state = TwoFactorAuthState(),
            event = {},
        )
    }
}
