package com.estexis.core.android.core

enum class ApiErrorCode(val raw: String) {
    AUTH_INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS"),
    NO_INTERNET("NO_INTERNET"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun from(raw: String?): ApiErrorCode =
            entries.find { it.raw == raw } ?: UNKNOWN
    }
}
