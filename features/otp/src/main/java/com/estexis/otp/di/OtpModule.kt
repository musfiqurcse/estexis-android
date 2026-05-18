package com.estexis.otp.di

import com.estexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.estexis.otp.data.api.OtpApi
import com.estexis.otp.data.remote.OtpRemoteSource
import com.estexis.otp.data.remote.OtpRemoteSourceImpl
import com.estexis.otp.data.repository.AccountVerificationRepository
import com.estexis.otp.data.repository.AccountVerificationRepositoryImpl
import com.estexis.otp.domain.SendOtpForPasswordResetUseCase
import com.estexis.otp.domain.SendOtpForPasswordResetUseCaseImpl
import com.estexis.otp.domain.UpdatePasswordUseCase
import com.estexis.otp.domain.UpdatePasswordUseCaseImpl
import com.estexis.otp.domain.VerifyEmailUseCase
import com.estexis.otp.domain.VerifyEmailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OtpModule {

    @Binds @Singleton
    abstract fun bindOtpRemoteSource(impl: OtpRemoteSourceImpl): OtpRemoteSource

    @Binds @Singleton
    abstract fun bindOtpRepository(impl: AccountVerificationRepositoryImpl): AccountVerificationRepository

    @Binds @Singleton
    abstract fun bindVerifyEmailUseCase(impl: VerifyEmailUseCaseImpl): VerifyEmailUseCase

    @Binds @Singleton
    abstract fun bindUpdatePasswordUseCase(impl: UpdatePasswordUseCaseImpl): UpdatePasswordUseCase

    @Binds @Singleton
    abstract fun bindResendOtpUseCase(impl: SendOtpForPasswordResetUseCaseImpl): SendOtpForPasswordResetUseCase

    companion object {
        @Provides @Singleton
        fun provideOtpApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): OtpApi =
            retrofit.create(OtpApi::class.java)
    }
}
