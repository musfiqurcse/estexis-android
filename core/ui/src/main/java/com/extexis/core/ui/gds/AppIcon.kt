package com.extexis.core.ui.gds

import com.extexis.core.ui.R

sealed class AppIcon(val resId: Int) {

    data class Custom(val drawable: Int) : AppIcon(drawable)
    data object SplashBG : AppIcon(R.drawable.splash_bg)
    data object LogoGreen : AppIcon(R.drawable.ic_grihoo_green)
}
