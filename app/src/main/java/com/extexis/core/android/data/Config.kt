package com.extexis.core.android.data

import com.extexis.core.android.BuildConfig


object Config {

    const val API_BASE_URL = BuildConfig.API_BASE_URL

    const val REFRESH_TOKEN = "v1/auth/refresh"

    const val LOGIN = "v1/auth/login"

    const val LOG_OUT = "v1/auth/logout"

    const val REGISTER = "v1/auth/register"

    const val VERIFY_EMAIL = "v1/auth/verify-email"

    const val SEND_OTP = "v1/auth/resend-email-verification"

    const val SEND_OTP_FORGOT_PASSWORD = "v1/auth/forgot-password"

    const val RESET_PASSWORD = "v1/auth/reset-password"

    const val CHANGE_PASSWORD = "v1/auth/change-password"

    const val DELETE_ACCOUNT = "v1/users/me"

}
