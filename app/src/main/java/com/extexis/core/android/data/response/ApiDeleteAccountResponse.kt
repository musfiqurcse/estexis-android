package com.extexis.core.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiDeleteAccountResponse(
    @param:Json(name = "message")
    val message: String,
    @param:Json(name = "deleted_devices")
    val deletedDevices: Int,
    @param:Json(name = "deleted_plans")
    val deletedPlans: Int,
)
