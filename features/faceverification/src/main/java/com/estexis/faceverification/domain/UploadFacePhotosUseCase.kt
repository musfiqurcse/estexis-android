package com.estexis.faceverification.domain

import android.net.Uri
import com.estexis.core.common.ApiResult
import com.estexis.faceverification.data.repository.FaceVerificationRepository
import javax.inject.Inject

interface UploadFacePhotosUseCase {
    suspend operator fun invoke(submissionId: String, photos: List<Uri>): ApiResult<Unit>
}

class UploadFacePhotosUseCaseImpl @Inject constructor(
    private val repository: FaceVerificationRepository,
) : UploadFacePhotosUseCase {
    override suspend fun invoke(submissionId: String, photos: List<Uri>): ApiResult<Unit> =
        repository.uploadFacePhotos(submissionId, photos)
}
