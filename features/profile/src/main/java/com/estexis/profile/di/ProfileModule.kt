package com.estexis.profile.di

import com.estexis.profile.data.api.ProfileApi
import com.estexis.profile.data.remote.ProfileRemoteSource
import com.estexis.profile.data.remote.ProfileRemoteSourceImpl
import com.estexis.profile.data.repository.ProfileRepository
import com.estexis.profile.data.repository.ProfileRepositoryImpl
import com.estexis.profile.domain.LogoutUseCase
import com.estexis.profile.domain.LogoutUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds @Singleton
    abstract fun bindProfileRemoteSource(impl: ProfileRemoteSourceImpl): ProfileRemoteSource

    @Binds @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds @Singleton
    abstract fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    companion object {
        @Provides @Singleton
        fun provideProfileApi(retrofit: Retrofit): ProfileApi =
            retrofit.create(ProfileApi::class.java)
    }
}
