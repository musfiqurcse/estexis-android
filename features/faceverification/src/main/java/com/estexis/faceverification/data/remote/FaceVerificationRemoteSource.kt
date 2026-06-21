package com.estexis.faceverification.data.remote

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.estexis.core.common.ApiResult
import com.estexis.core.network.compressImage
import com.estexis.core.network.executeSafeApiCall
import com.estexis.faceverification.data.api.FaceVerificationApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class FaceVerificationRemoteSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: FaceVerificationApi,
) {
    suspend fun uploadFacePhotos(submissionId: String, photos: List<Uri>): ApiResult<Unit> {
        val (leftBytes, rightBytes, straightBytes) = withContext(Dispatchers.IO) {
            Triple(
                compressImage(context, photos[0], maxDimension = 512, quality = 60),
                compressImage(context, photos[1], maxDimension = 512, quality = 60),
                compressImage(context, photos[2], maxDimension = 512, quality = 60),
            )
        }

        if (leftBytes == null || rightBytes == null || straightBytes == null) {
            return ApiResult.Error(message = "Failed to compress face images")
        }

        fun toBase64Part(name: String, bytes: ByteArray): MultipartBody.Part {
            val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
            return MultipartBody.Part.createFormData(name, base64)
        }

        val result = executeSafeApiCall {
            api.uploadFacePhotos(
                submissionId = submissionId,
                leftImage = toBase64Part("left_image", leftBytes),
                rightImage = toBase64Part("right_image", rightBytes),
                straightImage = toBase64Part("straight_image", straightBytes),
            )
        }
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(Unit)
            is ApiResult.Error -> result
        }
    }
}
