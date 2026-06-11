package com.estexis.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.ProfileMenuRow
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.profile.R

@Composable
fun ProfileScreen(
    state: ProfileState,
    event: (ProfileUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensions.spaces.x4),
    ) {
        VerticalSpacer(dimensions.spaces.x4)

        AvatarWithBadge()

        VerticalSpacer(dimensions.spaces.x3)

        Text(
            text = state.name,
            style = AppTheme.typography.BodyText1Bold,
            color = colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalSpacer(dimensions.spaces.x1)

        Text(
            text = state.email,
            style = AppTextStyles.BodyText3Regular,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalSpacer(dimensions.spaces.x6)

        Column(verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x3)) {
            ProfileMenuRow(
                image = AppIcon.UserAccount.resId,
                label = stringResource(R.string.profile_screen_account_information),
                onClick = { event(ProfileUiEvent.AccountInformationClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.Language.resId,
                label = stringResource(R.string.profile_screen_language),
                onClick = { event(ProfileUiEvent.LanguageClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.Security.resId,
                label = stringResource(R.string.profile_screen_security),
                onClick = { event(ProfileUiEvent.SecurityClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.DocumentValidation.resId,
                label = stringResource(R.string.profile_screen_kyc),
                onClick = { event(ProfileUiEvent.KycClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.Notification.resId,
                label = stringResource(R.string.profile_screen_notification),
                onClick = { event(ProfileUiEvent.NotificationClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.DocumentValidation.resId,
                label = stringResource(R.string.profile_screen_terms_condition),
                onClick = { event(ProfileUiEvent.TermsClicked) },
            )
            ProfileMenuRow(
                image = AppIcon.PrivacyPolicy.resId,
                label = stringResource(R.string.profile_screen_privacy_policy),
                onClick = { event(ProfileUiEvent.PrivacyPolicyClicked) },
            )

            LogoutRow(
                isLoggingOut = state.isLoggingOut,
                onClick = { event(ProfileUiEvent.LogoutClicked) },
            )
        }

        VerticalSpacer(dimensions.spaces.x8)
    }

    if (state.showLogoutDialog) {
        LogoutConfirmDialog(
            isLoggingOut = state.isLoggingOut,
            error = state.logoutError,
            onConfirm = { event(ProfileUiEvent.ConfirmLogout) },
            onDismiss = { event(ProfileUiEvent.DismissLogoutDialog) },
        )
    }
}

@Composable
private fun LogoutRow(
    isLoggingOut: Boolean,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(dimensions.sizes.x13)
            .clip(RoundedCornerShape(dimensions.radius.pill))
            .background(colors.white)
            .clickable(enabled = !isLoggingOut, onClick = onClick)
            .padding(horizontal = dimensions.spaces.x4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = null,
            tint = colors.error,
            modifier = Modifier.size(dimensions.sizes.x6),
        )
        Text(
            text = "Logout",
            style = AppTextStyles.BodyText2Regular,
            color = colors.error,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensions.spaces.x3),
        )
        if (isLoggingOut) {
            CircularProgressIndicator(
                color = colors.error,
                modifier = Modifier.size(dimensions.sizes.x5),
                strokeWidth = dimensions.borders.low,
            )
        }
    }
}

@Composable
private fun LogoutConfirmDialog(
    isLoggingOut: Boolean,
    error: String?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = AppTheme.colors

    AlertDialog(
        onDismissRequest = { if (!isLoggingOut) onDismiss() },
        title = { Text(text = "Logout") },
        text = {
            Text(
                text = error ?: "Are you sure you want to logout?",
                color = if (error != null) colors.error else colors.onBackground,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = !isLoggingOut,
            ) {
                Text(text = "Logout", color = colors.error)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoggingOut,
            ) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
private fun AvatarWithBadge() {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.size(dimensions.sizes.x32)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(colors.surface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = colors.surfaceDim,
                    modifier = Modifier.size(dimensions.sizes.x18),
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(dimensions.sizes.x11)
                    .clip(CircleShape)
                    .background(Color(0xFF2A2A2A)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(dimensions.sizes.x6),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ExtexisAndroidTheme {
        ProfileScreen(
            state = ProfileState(),
            event = {},
        )
    }
}
