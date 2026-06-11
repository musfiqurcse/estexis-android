package com.estexis.kyc.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.kyc.data.request.KycSubmitRequest
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KycApi {

    @GET(NetworkConfig.KYC_SUBMISSIONS)
    suspend fun getSubmissions(): Response<List<ApiKycSubmissionResponse>>

    @POST(NetworkConfig.KYC_SUBMIT)
    suspend fun submitKyc(
        @Path("id") id: String,
        @Body request: KycSubmitRequest,
    ): Response<ApiKycSubmitResponse>
}
