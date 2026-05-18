package com.estexis.core.data.di

import com.estexis.core.data.AuthVerificationRepositoryImpl
import com.estexis.core.domain.AuthVerificationRepository
import com.estexis.core.domain.SendOtpForVerificationUseCase
import com.estexis.core.domain.SendOtpForVerificationUseCaseImpl
import com.estexis.core.network.AuthVerificationApi
import com.estexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthVerificationModule {

    @Binds @Singleton
    abstract fun bindAuthVerificationRepository(
        impl: AuthVerificationRepositoryImpl,
    ): AuthVerificationRepository

    @Binds @Singleton
    abstract fun bindSendOtpForVerificationUseCase(
        impl: SendOtpForVerificationUseCaseImpl,
    ): SendOtpForVerificationUseCase

    companion object {
        @Provides @Singleton
        fun provideAuthVerificationApi(
            @RetrofitNoAuthorizationHeader retrofit: Retrofit,
        ): AuthVerificationApi = retrofit.create(AuthVerificationApi::class.java)
    }
}
