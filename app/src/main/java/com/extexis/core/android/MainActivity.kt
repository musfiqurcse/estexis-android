package com.extexis.core.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.extexis.core.android.navigation.AppNavigation
import com.extexis.core.android.navigation.rememberAppNavState
import com.extexis.core.ui.theme.ExtexisAndroidTheme
import com.extexis.core.android.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val appNavState = rememberAppNavState()
            val onBoardingState by viewModel.onboardingState.collectAsStateWithLifecycle()

            ExtexisAndroidTheme {
                AppNavigation(
                    appNavState = appNavState,
                    onboardingState = onBoardingState
                )
            }

        }
    }
}
