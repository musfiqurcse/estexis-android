package com.estexis.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class DocumentVerificationRoute(
    val type: DocumentVerificationType,
    val submissionId: String = "",
)
