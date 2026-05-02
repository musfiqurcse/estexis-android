package com.extexis.core.android.util.validation

import com.extexis.core.android.util.UiText

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UiText? = null,
)
