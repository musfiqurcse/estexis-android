package com.estexis.login.di

import com.estexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.estexis.login.data.api.LoginApi
import com.estexis.login.data.repository.LoginRepository
import com.estexis.login.data.repository.LoginRepositoryImpl
import com.estexis.login.domain.LoginUseCase
import com.estexis.login.domain.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Binds @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds @Singleton
    abstract fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    companion object {
        @Provides @Singleton
        fun provideLoginApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): LoginApi =
            retrofit.create(LoginApi::class.java)
    }
}
