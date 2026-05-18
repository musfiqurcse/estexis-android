package com.estexis.core.network.di

import com.estexis.core.network.BuildConfig
import com.estexis.core.network.SessionManager
import com.estexis.core.network.TokenProvider
import com.estexis.core.network.TokenRefreshService
import com.estexis.core.network.interceptors.AuthorizationTokenInterceptor
import com.estexis.core.network.qualifiers.AcceptHeaderInterceptorQualifier
import com.estexis.core.network.qualifiers.AuthorizationHeaderInterceptor
import com.estexis.core.network.qualifiers.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object InterceptorsModule {

    @Provides
    @Reusable
    @AcceptHeaderInterceptorQualifier
    fun provideAcceptHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
            )
        }
    }

    @Provides
    @Reusable
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.IS_DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
    }

    @Provides
    @Reusable
    @AuthorizationHeaderInterceptor
    fun provideAuthorizationInterceptor(
        tokenProvider: TokenProvider,
        tokenRefreshService: TokenRefreshService,
        sessionManager: SessionManager,
    ): Interceptor {
        return AuthorizationTokenInterceptor(tokenProvider, tokenRefreshService, sessionManager)
    }
}
