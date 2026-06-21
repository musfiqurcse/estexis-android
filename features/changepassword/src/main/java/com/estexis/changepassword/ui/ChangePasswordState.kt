package com.estexis.changepassword.ui

import com.estexis.core.ui.util.UiText

data class ChangePasswordState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val currentPasswordError: UiText? = null,
    val newPasswordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
)
