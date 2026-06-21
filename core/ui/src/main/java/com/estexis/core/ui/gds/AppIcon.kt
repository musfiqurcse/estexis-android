package com.estexis.core.ui.gds

import com.estexis.core.ui.R

sealed class AppIcon(val resId: Int) {

    data class Custom(val drawable: Int) : AppIcon(drawable)
    data object SplashBG : AppIcon(R.drawable.splash_bg)
    data object LogoGreen : AppIcon(R.drawable.ic_grihoo_green)
    data object ArrowForward : AppIcon(R.drawable.ic_arrow_forward)
    data object ArrowBackward : AppIcon(R.drawable.ic_arrow_back)
    data object Mail : AppIcon(R.drawable.ic_mail)
    data object LockPassword : AppIcon(R.drawable.ic_lock_password)
    data object VisibilityOff : AppIcon(R.drawable.ic_visibility_off)
    data object VisibilityOn : AppIcon(R.drawable.ic_lock_password)

    data object UserAccount : AppIcon(R.drawable.ic_user_account)
    data object Language : AppIcon(R.drawable.ic_language)
    data object Security : AppIcon(R.drawable.ic_ai_security)
    data object DocumentValidation : AppIcon(R.drawable.ic_document_validation)
    data object Notification : AppIcon(R.drawable.ic_notification)
    data object PrivacyPolicy : AppIcon(R.drawable.ic_privacy_policy)
    data object NID : AppIcon(R.drawable.ic_nid)

    data object NidVerification : AppIcon(R.drawable.ic_nid_verification)
    data object Passport : AppIcon(R.drawable.ic_passport)
    data object License : AppIcon(R.drawable.ic_license)
    data object BankStatement : AppIcon(R.drawable.ic_bank)
    data object Invoice : AppIcon(R.drawable.ic_invoice)

    data object CoverPage : AppIcon(R.drawable.ic_cover_page)
    data object DataPage : AppIcon(R.drawable.ic_data_page)
    data object DatePicker : AppIcon(R.drawable.ic_date_picker)
}
