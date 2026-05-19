package com.estexis.login.ui

import com.estexis.core.ui.util.UiText

data class LoginScreenUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
)

data class LoginFormState(
    val email: String = "",
    val password: String = "",
)
