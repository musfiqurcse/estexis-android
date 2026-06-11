package com.estexis.kyc.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiKycSubmitResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "user_id") val userId: String,
    @field:Json(name = "method") val method: String,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "attempt_number") val attemptNumber: Int,
    @field:Json(name = "rejection_reason") val rejectionReason: String?,
    @field:Json(name = "submitted_at") val submittedAt: String,
    @field:Json(name = "reviewed_at") val reviewedAt: String?,
)
