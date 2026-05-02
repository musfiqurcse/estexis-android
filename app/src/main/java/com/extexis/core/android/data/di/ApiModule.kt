package com.extexis.core.android.data.di

import com.extexis.core.android.data.api.AuthenticationApi
import com.extexis.core.android.data.api.OtpVerificationApi
import com.extexis.core.android.data.api.UserApi
import com.extexis.core.android.data.qualifiers.RetrofitNoAuthorizationHeader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideAuthenticationApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }

    @Provides
    fun provideOtpVerificationApi(@RetrofitNoAuthorizationHeader retrofit: Retrofit): OtpVerificationApi {
        return retrofit.create(OtpVerificationApi::class.java)
    }

}
