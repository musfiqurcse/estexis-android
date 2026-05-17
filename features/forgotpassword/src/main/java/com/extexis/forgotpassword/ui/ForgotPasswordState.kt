package com.extexis.forgotpassword.ui

import com.extexis.core.ui.util.UiText

data class ForgotPasswordState(
    val email: String = "info.anikdey003@gmail.com",
    val lastName: String = "Dey",
    val isLoading: Boolean = false,
    val emailError: UiText? = null,
    val lastNameError: UiText? = null,
)
