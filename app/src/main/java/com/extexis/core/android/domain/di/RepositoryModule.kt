package com.extexis.core.android.domain.di

import com.extexis.core.android.domain.repositories.AuthenticationRepository
import com.extexis.core.android.domain.repositories.AuthenticationRepositoryImpl
import com.extexis.core.android.domain.repositories.OtpVerificationRepository
import com.extexis.core.android.domain.repositories.OtpVerificationRepositoryImpl
import com.extexis.core.android.domain.repositories.SessionRepository
import com.extexis.core.android.domain.repositories.SessionRepositoryImpl
import com.extexis.core.android.domain.repositories.UserRepository
import com.extexis.core.android.domain.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthenticationRepository(authenticationRepository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun provideOtpVerificationRepository(otpVerificationRepository: OtpVerificationRepositoryImpl): OtpVerificationRepository

    @Binds
    @Singleton
    abstract fun provideUserRepositoryImpl(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideSessionRepositoryImpl(sessionRepository: SessionRepositoryImpl): SessionRepository

}
