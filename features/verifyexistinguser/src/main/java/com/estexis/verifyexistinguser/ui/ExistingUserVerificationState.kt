package com.estexis.verifyexistinguser.ui

import com.estexis.core.ui.util.UiText

data class ExistingUserVerificationState(
    val email: String = "",
    val lastName: String = "",
    val isLoading: Boolean = false,
    val emailError: UiText? = null,
    val lastNameError: UiText? = null,
)
