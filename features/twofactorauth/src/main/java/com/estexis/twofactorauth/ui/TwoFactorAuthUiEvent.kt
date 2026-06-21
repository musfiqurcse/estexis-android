package com.estexis.twofactorauth.ui

sealed class TwoFactorAuthUiEvent {
    object ToggleClicked : TwoFactorAuthUiEvent()
    object BackClicked : TwoFactorAuthUiEvent()
}

sealed class TwoFactorAuthNavigationEvent {
    object Back : TwoFactorAuthNavigationEvent()
}
