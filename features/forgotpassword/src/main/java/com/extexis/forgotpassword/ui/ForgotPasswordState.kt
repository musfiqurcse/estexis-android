package com.extexis.forgotpassword.ui

data class ForgotPasswordState(
    val email: String = "info.anikdey003@gmail.com",
    val isLoading: Boolean = false,
    val emailError: String? = null,
)
