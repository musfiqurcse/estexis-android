package com.estexis.twofactorauth.di

import com.estexis.twofactorauth.data.api.TwoFactorAuthApi
import com.estexis.twofactorauth.data.remote.TwoFactorAuthRemoteSource
import com.estexis.twofactorauth.data.remote.TwoFactorAuthRemoteSourceImpl
import com.estexis.twofactorauth.data.repository.TwoFactorAuthRepository
import com.estexis.twofactorauth.data.repository.TwoFactorAuthRepositoryImpl
import com.estexis.twofactorauth.domain.ToggleTwoFactorAuthUseCase
import com.estexis.twofactorauth.domain.ToggleTwoFactorAuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TwoFactorAuthModule {

    @Binds @Singleton
    abstract fun bindTwoFactorAuthRemoteSource(impl: TwoFactorAuthRemoteSourceImpl): TwoFactorAuthRemoteSource

    @Binds @Singleton
    abstract fun bindTwoFactorAuthRepository(impl: TwoFactorAuthRepositoryImpl): TwoFactorAuthRepository

    @Binds @Singleton
    abstract fun bindToggleTwoFactorAuthUseCase(impl: ToggleTwoFactorAuthUseCaseImpl): ToggleTwoFactorAuthUseCase

    companion object {
        @Provides @Singleton
        fun provideTwoFactorAuthApi(retrofit: Retrofit): TwoFactorAuthApi =
            retrofit.create(TwoFactorAuthApi::class.java)
    }
}
