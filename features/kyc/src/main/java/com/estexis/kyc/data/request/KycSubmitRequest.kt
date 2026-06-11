package com.estexis.kyc.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KycSubmitRequest(
    @field:Json(name = "document_type") val documentType: String,
    @field:Json(name = "document_number") val documentNumber: String,
    @field:Json(name = "date_of_birth") val dateOfBirth: String,
    @field:Json(name = "expiry_date") val expiryDate: String,
    @field:Json(name = "country_of_issue") val countryOfIssue: String,
    @field:Json(name = "files") val files: List<String>,
)
