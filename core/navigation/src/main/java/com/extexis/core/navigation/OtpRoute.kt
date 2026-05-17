package com.extexis.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class OtpRoute(val email: String, val purpose: OtpPurpose, val lastName: String = "")
