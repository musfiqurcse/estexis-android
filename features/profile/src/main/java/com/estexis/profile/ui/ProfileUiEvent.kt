package com.estexis.profile.ui

sealed class ProfileUiEvent {
    object BackClicked : ProfileUiEvent()
    object AccountInformationClicked : ProfileUiEvent()
    object LanguageClicked : ProfileUiEvent()
    object SecurityClicked : ProfileUiEvent()
    object KycClicked : ProfileUiEvent()
    object NotificationClicked : ProfileUiEvent()
    object TermsClicked : ProfileUiEvent()
    object PrivacyPolicyClicked : ProfileUiEvent()
}

sealed class ProfileNavigationEvent {
    object Back : ProfileNavigationEvent()
    object ToKyc : ProfileNavigationEvent()
}
