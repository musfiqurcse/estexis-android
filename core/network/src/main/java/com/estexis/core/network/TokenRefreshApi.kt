package com.estexis.core.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenRefreshApi {

    @POST(NetworkConfig.REFRESH_TOKEN)
    suspend fun refreshToken(@Body request: TokenRefreshRequest): Response<TokenRefreshResponse>
}

@JsonClass(generateAdapter = true)
data class TokenRefreshRequest(
    @Json(name = "refresh_token") val refreshToken: String,
)

@JsonClass(generateAdapter = true)
data class TokenRefreshResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
)
