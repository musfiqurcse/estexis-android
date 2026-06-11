package com.estexis.kyc.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiKycSubmissionResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "method") val method: String,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "attempt_number") val attemptNumber: Int,
    @field:Json(name = "submitted_at") val submittedAt: String,
    @field:Json(name = "has_removal_request") val hasRemovalRequest: Boolean,
    @field:Json(name = "has_face_liveness") val hasFaceLiveness: Boolean,
)
