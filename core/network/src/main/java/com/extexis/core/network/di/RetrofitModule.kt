package com.extexis.core.network.di

import android.content.Context
import com.extexis.core.network.NetworkConfig
import com.extexis.core.network.TokenRefreshApi
import com.extexis.core.network.qualifiers.AcceptHeaderInterceptorQualifier
import com.extexis.core.network.qualifiers.AuthorizationHeaderInterceptor
import com.extexis.core.network.qualifiers.LoggingInterceptor
import com.extexis.core.network.qualifiers.OkHttpNoAuthorizationHeader
import com.extexis.core.network.qualifiers.RetrofitNoAuthorizationHeader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val TIME_OUT = 60L
    private const val CACHE_SIZE: Long = 500 * 1024 * 1024

    @Provides
    @Reusable
    fun provideHttpCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @AcceptHeaderInterceptorQualifier acceptHeaderInterceptor: Interceptor,
        @AuthorizationHeaderInterceptor authorizationTokenInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor(acceptHeaderInterceptor)
            .addInterceptor(authorizationTokenInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @OkHttpNoAuthorizationHeader
    fun provideOkHttpClientNoAuth(
        @AcceptHeaderInterceptorQualifier acceptHeaderInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor(acceptHeaderInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @RetrofitNoAuthorizationHeader
    fun provideRetrofitNoAuth(
        @OkHttpNoAuthorizationHeader okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenRefreshApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): TokenRefreshApi {
        return retrofit.create(TokenRefreshApi::class.java)
    }
}
