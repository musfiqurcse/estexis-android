package com.estexis.faceverification.di

import com.estexis.faceverification.data.api.FaceVerificationApi
import com.estexis.faceverification.data.repository.FaceVerificationRepository
import com.estexis.faceverification.data.repository.FaceVerificationRepositoryImpl
import com.estexis.faceverification.domain.UploadFacePhotosUseCase
import com.estexis.faceverification.domain.UploadFacePhotosUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FaceVerificationModule {

    @Binds @Singleton
    abstract fun bindFaceVerificationRepository(
        impl: FaceVerificationRepositoryImpl,
    ): FaceVerificationRepository

    @Binds @Singleton
    abstract fun bindUploadFacePhotosUseCase(
        impl: UploadFacePhotosUseCaseImpl,
    ): UploadFacePhotosUseCase

    companion object {
        @Provides @Singleton
        fun provideFaceVerificationApi(retrofit: Retrofit): FaceVerificationApi =
            retrofit.create(FaceVerificationApi::class.java)
    }
}
