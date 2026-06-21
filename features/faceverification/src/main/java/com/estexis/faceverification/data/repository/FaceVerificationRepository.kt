package com.estexis.faceverification.data.repository

import android.net.Uri
import com.estexis.core.common.ApiResult

interface FaceVerificationRepository {
    suspend fun uploadFacePhotos(submissionId: String, photos: List<Uri>): ApiResult<Unit>
}
