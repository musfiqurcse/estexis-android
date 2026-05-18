package com.estexis.welcome.ui

sealed class WelcomeScreenUiEvent {

    object GetStarted : WelcomeScreenUiEvent()
}

sealed class WelcomeScreenNavigationEvent {

    data object ToLogin : WelcomeScreenNavigationEvent()
}
