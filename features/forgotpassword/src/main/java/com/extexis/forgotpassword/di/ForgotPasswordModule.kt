package com.extexis.forgotpassword.di

import com.extexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.extexis.forgotpassword.data.api.ForgotPasswordApi
import com.extexis.forgotpassword.data.remote.ForgotPasswordRemoteSource
import com.extexis.forgotpassword.data.remote.ForgotPasswordRemoteSourceImpl
import com.extexis.forgotpassword.data.repository.ForgotPasswordRepository
import com.extexis.forgotpassword.data.repository.ForgotPasswordRepositoryImpl
import com.extexis.forgotpassword.domain.SendOtpForForgotPasswordUseCase
import com.extexis.forgotpassword.domain.SendOtpForForgotPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ForgotPasswordModule {

    @Binds @Singleton
    abstract fun bindForgotPasswordRemoteSource(impl: ForgotPasswordRemoteSourceImpl): ForgotPasswordRemoteSource

    @Binds @Singleton
    abstract fun bindForgotPasswordRepository(impl: ForgotPasswordRepositoryImpl): ForgotPasswordRepository

    @Binds @Singleton
    abstract fun bindSendOtpForForgotPasswordUseCase(impl: SendOtpForForgotPasswordUseCaseImpl): SendOtpForForgotPasswordUseCase

    companion object {
        @Provides @Singleton
        fun provideForgotPasswordApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): ForgotPasswordApi =
            retrofit.create(ForgotPasswordApi::class.java)
    }
}
