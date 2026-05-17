package com.extexis.core.ui.gds

import com.extexis.core.ui.R

sealed class AppIcon(val resId: Int) {

    data class Custom(val drawable: Int) : AppIcon(drawable)
    data object SplashBG : AppIcon(R.drawable.ic_splash_bg)
    data object LogoGreen : AppIcon(R.drawable.ic_grihoo_green)
    data object IcArrowForward : AppIcon(R.drawable.ic_arrow_forward)
    data object IcMail : AppIcon(R.drawable.ic_mail)
    data object IcLockPassword : AppIcon(R.drawable.ic_lock_password)
    data object IcVisibilityOff : AppIcon(R.drawable.ic_visibility_off)
    data object IcVisibilityOn : AppIcon(R.drawable.ic_lock_password)
}
