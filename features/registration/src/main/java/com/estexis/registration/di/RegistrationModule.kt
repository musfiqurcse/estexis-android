package com.estexis.registration.di

import com.estexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.estexis.registration.data.api.RegistrationApi
import com.estexis.registration.data.remote.RegistrationRemoteSource
import com.estexis.registration.data.remote.RegistrationRemoteSourceImpl
import com.estexis.registration.data.repository.RegistrationRepository
import com.estexis.registration.data.repository.RegistrationRepositoryImpl
import com.estexis.registration.domain.RegistrationUseCase
import com.estexis.registration.domain.RegistrationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegistrationModule {

    @Binds @Singleton
    abstract fun bindRegistrationRemoteSource(impl: RegistrationRemoteSourceImpl): RegistrationRemoteSource

    @Binds @Singleton
    abstract fun bindRegistrationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository

    @Binds @Singleton
    abstract fun bindRegistrationUseCase(impl: RegistrationUseCaseImpl): RegistrationUseCase

    companion object {
        @Provides @Singleton
        fun provideRegistrationApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): RegistrationApi =
            retrofit.create(RegistrationApi::class.java)
    }
}
