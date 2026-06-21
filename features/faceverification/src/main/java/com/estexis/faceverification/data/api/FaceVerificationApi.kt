package com.estexis.faceverification.data.api

import com.estexis.core.network.NetworkConfig
import com.estexis.faceverification.data.response.FaceVerificationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FaceVerificationApi {

    @Multipart
    @POST(NetworkConfig.FACE_PHOTOS)
    suspend fun uploadFacePhotos(
        @Path("submission_id") submissionId: String,
        @Part leftImage: MultipartBody.Part,
        @Part rightImage: MultipartBody.Part,
        @Part straightImage: MultipartBody.Part,
    ): Response<FaceVerificationResponse>
}
