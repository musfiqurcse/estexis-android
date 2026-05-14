package com.extexis.registration.di

import com.extexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.extexis.registration.data.api.RegistrationApi
import com.extexis.registration.data.remote.RegistrationRemoteSource
import com.extexis.registration.data.remote.RegistrationRemoteSourceImpl
import com.extexis.registration.data.repository.RegistrationRepository
import com.extexis.registration.data.repository.RegistrationRepositoryImpl
import com.extexis.registration.domain.RegistrationUseCase
import com.extexis.registration.domain.RegistrationUseCaseImpl
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
