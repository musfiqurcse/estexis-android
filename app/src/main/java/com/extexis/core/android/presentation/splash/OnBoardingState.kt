package com.extexis.core.android.presentation.splash

sealed class OnBoardingState {

    data object OnBoarded : OnBoardingState()

    data object FirstLaunch : OnBoardingState()
}
