package com.extexis.registration.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "last_name") val lastName: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "phone_number") val phoneNumber: String,
    @field:Json(name = "confirm_password") val confirmPassword: String,
)

//"role": "buyer",
//"account_type": "personal",
//"country_code": "st",
//"company_name": "string",
//"contact_person_name": "string",
//"invitation_token": "string"