package com.extexis.core.network.di

import com.extexis.core.network.BuildConfig
import com.extexis.core.network.SessionManager
import com.extexis.core.network.TokenProvider
import com.extexis.core.network.TokenRefreshService
import com.extexis.core.network.interceptors.AuthorizationTokenInterceptor
import com.extexis.core.network.qualifiers.AcceptHeaderInterceptorQualifier
import com.extexis.core.network.qualifiers.AuthorizationHeaderInterceptor
import com.extexis.core.network.qualifiers.LoggingInterceptor
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
