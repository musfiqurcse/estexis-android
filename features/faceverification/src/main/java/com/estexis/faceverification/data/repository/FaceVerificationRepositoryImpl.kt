package com.estexis.faceverification.data.repository

import android.net.Uri
import com.estexis.core.common.ApiResult
import com.estexis.faceverification.data.remote.FaceVerificationRemoteSource
import javax.inject.Inject

class FaceVerificationRepositoryImpl @Inject constructor(
    private val remoteSource: FaceVerificationRemoteSource,
) : FaceVerificationRepository {

    override suspend fun uploadFacePhotos(submissionId: String, photos: List<Uri>): ApiResult<Unit> =
        remoteSource.uploadFacePhotos(submissionId, photos)
}
