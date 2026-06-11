package com.estexis.profile.ui

data class ProfileState(
    val name: String = "John Iyee",
    val email: String = "johnlyee@email.com",
    val showLogoutDialog: Boolean = false,
    val isLoggingOut: Boolean = false,
    val logoutError: String? = null,
)
