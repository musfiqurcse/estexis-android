package com.extexis.core.android.data.di

import com.extexis.core.android.data.Config.API_BASE_URL
import com.extexis.core.android.data.qualifiers.AppBaseUrlQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppBaseUrlModule {


    companion object {

        @Provides
        @Singleton
        @AppBaseUrlQualifier
        fun provideTravelTrouveAppBaseUrl(): String {
            return API_BASE_URL
        }

    }

}
