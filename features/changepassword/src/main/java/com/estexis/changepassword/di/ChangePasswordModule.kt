package com.estexis.changepassword.di

import com.estexis.changepassword.data.api.ChangePasswordApi
import com.estexis.changepassword.data.remote.ChangePasswordRemoteSource
import com.estexis.changepassword.data.remote.ChangePasswordRemoteSourceImpl
import com.estexis.changepassword.data.repository.ChangePasswordRepository
import com.estexis.changepassword.data.repository.ChangePasswordRepositoryImpl
import com.estexis.changepassword.domain.ChangePasswordUseCase
import com.estexis.changepassword.domain.ChangePasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChangePasswordModule {

    @Binds @Singleton
    abstract fun bindChangePasswordRemoteSource(impl: ChangePasswordRemoteSourceImpl): ChangePasswordRemoteSource

    @Binds @Singleton
    abstract fun bindChangePasswordRepository(impl: ChangePasswordRepositoryImpl): ChangePasswordRepository

    @Binds @Singleton
    abstract fun bindChangePasswordUseCase(impl: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    companion object {
        @Provides @Singleton
        fun provideChangePasswordApi(retrofit: Retrofit): ChangePasswordApi =
            retrofit.create(ChangePasswordApi::class.java)
    }
}
