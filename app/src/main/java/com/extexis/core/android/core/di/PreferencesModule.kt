package com.extexis.core.android.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.AppOnBoardingPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import com.extexis.core.android.core.preference.AppPreference
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class PreferencesModule {

    companion object {

        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            val preferenceName = context.packageName + ".travelplanner"
            val dataStore = PreferenceDataStoreFactory.create {
                context.applicationContext.preferencesDataStoreFile(preferenceName)
            }
            return dataStore
        }

    }

    @Binds
    @Singleton
    abstract fun provideAppOnBoardingPreference(appOnBoardingPreference: AppOnBoardingPreference): AppPreference<Boolean>

    @Binds
    @Singleton
    abstract fun provideAccessTokenPreference(accessTokenPreference: AccessTokenPreference): AppPreference<String>

    @Binds
    @Singleton
    abstract fun provideRefreshTokenPreference(refreshTokenPreference: RefreshTokenPreference): AppPreference<String>

}
