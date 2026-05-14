package com.extexis.core.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.extexis.core.android.presentation.home.homeNavGraph
import com.extexis.core.android.presentation.splash.OnBoardingState
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.SplashScreenRoute
import com.extexis.forgotpassword.ui.forgotPasswordNavGraph
import com.extexis.login.ui.loginNavGraph
import com.extexis.otp.ui.otpNavGraph
import com.extexis.registration.ui.registrationNavGraph
import com.extexis.splash.ui.splashScreenNavGraph

@Composable
fun AppNavigation(
    appNavState: AppNavState,
    onboardingState: OnBoardingState,
    modifier: Modifier = Modifier,
) {
    val startDestination = when (onboardingState) {
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
        splashScreenNavGraph(navController)
        loginNavGraph(navController)
        registrationNavGraph(navController)
        forgotPasswordNavGraph(navController)
        otpNavGraph(navController)
        homeNavGraph(appNavState)
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
