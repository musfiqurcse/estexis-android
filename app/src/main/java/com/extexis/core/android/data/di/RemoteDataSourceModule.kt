package com.extexis.core.android.data.di

import com.extexis.core.android.data.remote.UserRemoteSource
import com.extexis.core.android.data.remote.UserRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun provideUserRemoteSource(userRemoteSource: UserRemoteSourceImpl): UserRemoteSource
}
