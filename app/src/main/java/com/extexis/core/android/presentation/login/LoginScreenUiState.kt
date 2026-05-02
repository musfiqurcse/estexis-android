package com.extexis.core.android.presentation.login

import com.extexis.core.android.util.UiText

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
