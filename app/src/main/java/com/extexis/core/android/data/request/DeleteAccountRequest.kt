package com.extexis.core.android.data.request

import com.squareup.moshi.Json

data class DeleteAccountRequest(
    @param:Json(name = "password")
    val password: String,
)
