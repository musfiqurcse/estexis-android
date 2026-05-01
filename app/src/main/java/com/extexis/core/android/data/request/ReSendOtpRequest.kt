package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class ReSendOtpRequest(
    @param:Json(name = "email")
    val email: String
)
