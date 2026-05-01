package com.extexis.core.android.data.di

import com.extexis.core.android.BuildConfig
import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import com.extexis.core.android.data.api.AuthenticationApi
import com.extexis.core.android.data.interceptors.AuthorizationTokenInterceptor
import com.extexis.core.android.domain.repositories.SessionRepository
import com.travelhugai.travelplanner.data.qualifiers.AuthorizationHeaderInterceptor
import com.travelhugai.travelplanner.data.qualifiers.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
abstract class InterceptorsModule {

    companion object {

        @Provides
        @Reusable
        @LoggingInterceptor
        fun providesLoggingInterceptor(): Interceptor {

            return HttpLoggingInterceptor {
                //Timber.tag("TTLogging").v(it)
            }.setLevel(
                if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            )
        }

        @Provides
        @Reusable
        @AuthorizationHeaderInterceptor
        fun provideAuthorizationHeader(
            accessTokenPreference: AccessTokenPreference,
            refreshTokenPreference: RefreshTokenPreference,
            authenticationApi: AuthenticationApi,
            sessionRepository: SessionRepository
        ): Interceptor {
            return AuthorizationTokenInterceptor(accessTokenPreference, refreshTokenPreference, authenticationApi, sessionRepository)
        }


    }
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}