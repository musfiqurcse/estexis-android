package com.extexis.core.android.data.di

import android.content.Context
import com.travelhugai.travelplanner.data.qualifiers.AcceptHeaderInterceptorQualifier
import com.travelhugai.travelplanner.data.qualifiers.AppBaseUrlQualifier
import com.travelhugai.travelplanner.data.qualifiers.AuthorizationHeaderInterceptor
import com.travelhugai.travelplanner.data.qualifiers.LoggingInterceptor
import com.travelhugai.travelplanner.data.qualifiers.OkHttpNoAuthorizationHeader
import com.travelhugai.travelplanner.data.qualifiers.RetrofitNoAuthorizationHeader
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

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrofitModule {

    companion object {

        private const val TIME_OUT = 60L
        private const val CACHE_SIZE: Long = 500 * 1024 * 1024 // 500 MB

        @Provides
        @Reusable
        fun provideHttpCache(@ApplicationContext context: Context): Cache {
            return Cache(context.cacheDir, CACHE_SIZE)
        }

        private fun createOkHttpClient(
            applicationInterceptors: List<Interceptor>,
            networkInterceptors: List<Interceptor>,
            cache: Cache
        ): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .callTimeout(TIME_OUT, TimeUnit.MINUTES)
                .cache(cache)

            for (interceptor in applicationInterceptors) builder.addInterceptor(interceptor)
            for (interceptor in networkInterceptors) builder.addInterceptor(interceptor)

            return builder.build()
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            @AcceptHeaderInterceptorQualifier acceptHeaderInterceptor: Interceptor,
            @AuthorizationHeaderInterceptor authorizationTokenInterceptor: Interceptor,
            @LoggingInterceptor loggingInterceptor: Interceptor,
            cache: Cache
        ): OkHttpClient {
            return createOkHttpClient(
                listOf(
                    acceptHeaderInterceptor,
                    authorizationTokenInterceptor
                ),
                listOf(loggingInterceptor),
                cache = cache
            )
        }

        @Provides
        @Singleton
        @OkHttpNoAuthorizationHeader
        fun provideOkHttpClientNoAuthorizationHeader(
            @AcceptHeaderInterceptorQualifier acceptHeaderInterceptor: Interceptor,
            @LoggingInterceptor loggingInterceptor: Interceptor,
            cache: Cache
        ): OkHttpClient {
            return createOkHttpClient(
                listOf(acceptHeaderInterceptor),
                listOf(loggingInterceptor),
                cache = cache
            )
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
        fun provideRetrofit(
            @AppBaseUrlQualifier baseUrl: String,
            okHttpClient: OkHttpClient,
            moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        @RetrofitNoAuthorizationHeader
        fun provideRetrofitNoAuthHeader(
            @AppBaseUrlQualifier baseUrl: String,
            @OkHttpNoAuthorizationHeader okHttpClient: OkHttpClient,
            moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
        }
    }
}
