package com.estexis.forgotpassword.di

import com.estexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.estexis.forgotpassword.data.api.ForgotPasswordApi
import com.estexis.forgotpassword.data.remote.ForgotPasswordRemoteSource
import com.estexis.forgotpassword.data.remote.ForgotPasswordRemoteSourceImpl
import com.estexis.forgotpassword.data.repository.ForgotPasswordRepository
import com.estexis.forgotpassword.data.repository.ForgotPasswordRepositoryImpl
import com.estexis.forgotpassword.domain.SendOtpForForgotPasswordUseCase
import com.estexis.forgotpassword.domain.SendOtpForForgotPasswordUseCaseImpl
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
