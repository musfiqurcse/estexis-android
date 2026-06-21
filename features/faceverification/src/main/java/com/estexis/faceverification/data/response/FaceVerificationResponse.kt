package com.estexis.faceverification.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FaceVerificationResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "kyc_submission_id") val kycSubmissionId: String,
    @field:Json(name = "left_image_key") val leftImageKey: String,
    @field:Json(name = "right_image_key") val rightImageKey: String,
    @field:Json(name = "straight_image_key") val straightImageKey: String,
    @field:Json(name = "uploaded_at") val uploadedAt: String,
)
