package com.extexis.core.android.data.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class RefreshTokenRequest(
    @param:Json(name = "refresh_token")
    val refreshToken: String
)
