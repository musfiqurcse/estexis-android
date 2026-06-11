package com.estexis.profile.ui

sealed class ProfileUiEvent {
    object AccountInformationClicked : ProfileUiEvent()
    object LanguageClicked : ProfileUiEvent()
    object SecurityClicked : ProfileUiEvent()
    object KycClicked : ProfileUiEvent()
    object NotificationClicked : ProfileUiEvent()
    object TermsClicked : ProfileUiEvent()
    object PrivacyPolicyClicked : ProfileUiEvent()
    object LogoutClicked : ProfileUiEvent()
    object ConfirmLogout : ProfileUiEvent()
    object DismissLogoutDialog : ProfileUiEvent()
}

sealed class ProfileNavigationEvent {
    object ToKyc : ProfileNavigationEvent()
    object ToLogin : ProfileNavigationEvent()
}
