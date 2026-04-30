package com.extexis.core.android.presentation.forgotpassword

data class ForgotPasswordState(
    val email: String = "info.anikdey003@gmail.com",
    val isLoading: Boolean = false,
    val emailError: String? = null,
)
