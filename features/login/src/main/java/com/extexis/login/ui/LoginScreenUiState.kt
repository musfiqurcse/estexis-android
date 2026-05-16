package com.extexis.login.ui

import com.extexis.core.ui.util.UiText

data class LoginScreenUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
)

data class LoginFormState(
    val email: String = "info.anikdey003@gmail.com",
    val password: String = "1qazZAQ!",
)
