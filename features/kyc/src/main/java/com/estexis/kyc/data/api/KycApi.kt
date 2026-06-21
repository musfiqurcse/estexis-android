package com.estexis.kyc.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface KycApi {

    @GET(NetworkConfig.KYC_SUBMISSIONS)
    suspend fun getSubmissions(): Response<List<ApiKycSubmissionResponse>>

    @Multipart
    @POST(NetworkConfig.KYC_SUBMIT)
    suspend fun submitKyc(
        @Part("document_type") documentType: RequestBody,
        @Part("document_number") documentNumber: RequestBody,
        @Part("date_of_birth") dateOfBirth: RequestBody,
        @Part("expiry_date") expiryDate: RequestBody,
        @Part("country_of_issue") countryOfIssue: RequestBody,
        @Part files: List<MultipartBody.Part>,
    ): Response<ApiKycSubmitResponse>
}
