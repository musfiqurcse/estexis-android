package com.estexis.core.android.domain.di

import com.estexis.core.android.core.network.AuthenticationApiTokenRefreshService
import com.estexis.core.android.core.network.DataStoreTokenProvider
import com.estexis.core.android.domain.repositories.SessionRepository
import com.estexis.core.android.domain.repositories.SessionRepositoryImpl
import com.estexis.core.android.domain.repositories.UserRepository
import com.estexis.core.android.domain.repositories.UserRepositoryImpl
import com.estexis.core.network.SessionManager
import com.estexis.core.network.TokenProvider
import com.estexis.core.network.TokenRefreshService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds @Singleton
    abstract fun provideSessionRepository(impl: SessionRepositoryImpl): SessionRepository

    @Binds @Singleton
    abstract fun provideSessionManager(impl: SessionRepositoryImpl): SessionManager

    @Binds @Singleton
    abstract fun provideTokenProvider(impl: DataStoreTokenProvider): TokenProvider

    @Binds @Singleton
    abstract fun provideTokenRefreshService(impl: AuthenticationApiTokenRefreshService): TokenRefreshService
}
