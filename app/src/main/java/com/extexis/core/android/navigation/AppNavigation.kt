package com.extexis.core.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.extexis.core.android.presentation.forgotpassword.ForgotPasswordRoute
import com.extexis.core.android.presentation.forgotpassword.forgotPasswordNavGraph
import com.extexis.core.android.presentation.login.LoginRoute
import com.extexis.core.android.presentation.login.loginNavGraph
import com.extexis.core.android.presentation.otp.otpNavGraph
import com.extexis.core.android.presentation.registration.registrationNavGraph
import com.extexis.core.android.presentation.splash.OnBoardingState
import com.extexis.core.android.presentation.splash.SplashScreenRoute
import com.extexis.core.android.presentation.splash.splashScreenNavGraph

@Composable
fun AppNavigation(
    appNavState: AppNavState,
    onboardingState: OnBoardingState,
    modifier: Modifier = Modifier,
) {
    val startDestination = when(onboardingState) {
        OnBoardingState.OnBoarded -> LoginRoute
        OnBoardingState.FirstLaunch -> SplashScreenRoute
    }

//    LaunchedEffect(authState) {
//        when(authState) {
//            AuthState.Authenticated -> {
//                appNavState.navHostController.navigate(WelcomeScreenRoute) {
//                    popUpTo(0) { inclusive = true  }
//                }
//            }
//            AuthState.NotAuthenticated -> {
//                appNavState.navHostController.navigate(AccessChoiceNavRoute) {
//                    popUpTo(0) { inclusive = true  }
//                }
//            }
//        }
//    }

    val navController = appNavState.navHostController

    NavHost(navController = navController, startDestination = startDestination) {

        splashScreenNavGraph(appNavState=appNavState)
        loginNavGraph(appNavState=appNavState)
        registrationNavGraph(appNavState=appNavState)
        forgotPasswordNavGraph(appNavState=appNavState)
        otpNavGraph(appNavState=appNavState)

    }
}

fun NavController.navigateToRoute(route: Any, popupTo: Any) {
    navigate(route) {
        popUpTo(popupTo) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
    }
}
