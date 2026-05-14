package com.extexis.otp.di

import com.extexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.extexis.otp.data.api.OtpApi
import com.extexis.otp.data.remote.OtpRemoteSource
import com.extexis.otp.data.remote.OtpRemoteSourceImpl
import com.extexis.otp.data.repository.OtpRepository
import com.extexis.otp.data.repository.OtpRepositoryImpl
import com.extexis.otp.domain.ResendOtpUseCase
import com.extexis.otp.domain.ResendOtpUseCaseImpl
import com.extexis.otp.domain.UpdatePasswordUseCase
import com.extexis.otp.domain.UpdatePasswordUseCaseImpl
import com.extexis.otp.domain.VerifyEmailUseCase
import com.extexis.otp.domain.VerifyEmailUseCaseImpl
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
    abstract fun bindOtpRepository(impl: OtpRepositoryImpl): OtpRepository

    @Binds @Singleton
    abstract fun bindVerifyEmailUseCase(impl: VerifyEmailUseCaseImpl): VerifyEmailUseCase

    @Binds @Singleton
    abstract fun bindUpdatePasswordUseCase(impl: UpdatePasswordUseCaseImpl): UpdatePasswordUseCase

    @Binds @Singleton
    abstract fun bindResendOtpUseCase(impl: ResendOtpUseCaseImpl): ResendOtpUseCase

    companion object {
        @Provides @Singleton
        fun provideOtpApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): OtpApi =
            retrofit.create(OtpApi::class.java)
    }
}
