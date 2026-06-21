package com.estexis.kyc.data.remote

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.estexis.core.common.ApiResult
import com.estexis.core.network.compressImage
import com.estexis.core.network.executeSafeApiCall
import com.estexis.kyc.data.api.KycApi
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class KycRemoteSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kycApi: KycApi,
) {
    suspend fun getSubmissions(): ApiResult<List<ApiKycSubmissionResponse>> {
        return executeSafeApiCall { kycApi.getSubmissions() }
    }

    suspend fun submitKyc(
        documentType: String,
        documentNumber: String,
        dateOfBirth: String,
        expiryDate: String,
        countryOfIssue: String,
        files: List<Uri>,
    ): ApiResult<ApiKycSubmitResponse> {
        val textPlain = "text/plain".toMediaType()
        val fileParts = withContext(Dispatchers.IO) {
            files.map { uri ->
                val bytes = compressImage(context, uri, maxDimension = 512, quality = 60) ?: return@map null
                val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
                MultipartBody.Part.createFormData("files", base64)
            }.filterNotNull()
        }

        return executeSafeApiCall {
            kycApi.submitKyc(
                documentType = documentType.toRequestBody(textPlain),
                documentNumber = documentNumber.toRequestBody(textPlain),
                dateOfBirth = dateOfBirth.toRequestBody(textPlain),
                expiryDate = expiryDate.toRequestBody(textPlain),
                countryOfIssue = countryOfIssue.toRequestBody(textPlain),
                files = fileParts,
            )
        }
    }
}
