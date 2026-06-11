package com.estexis.kyc.di

import com.estexis.kyc.data.api.KycApi
import com.estexis.kyc.data.repository.KycRepository
import com.estexis.kyc.data.repository.KycRepositoryImpl
import com.estexis.kyc.domain.GetKycSubmissionsUseCase
import com.estexis.kyc.domain.GetKycSubmissionsUseCaseImpl
import com.estexis.kyc.domain.SubmitKycUseCase
import com.estexis.kyc.domain.SubmitKycUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KycModule {

    @Binds @Singleton
    abstract fun bindKycRepository(impl: KycRepositoryImpl): KycRepository

    @Binds @Singleton
    abstract fun bindGetKycSubmissionsUseCase(impl: GetKycSubmissionsUseCaseImpl): GetKycSubmissionsUseCase

    @Binds @Singleton
    abstract fun bindSubmitKycUseCase(impl: SubmitKycUseCaseImpl): SubmitKycUseCase

    companion object {
        @Provides @Singleton
        fun provideKycApi(retrofit: Retrofit): KycApi =
            retrofit.create(KycApi::class.java)
    }
}
