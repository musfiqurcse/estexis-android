package com.extexis.core.android.core.di

import android.content.Context
import com.extexis.core.datastore.LocalePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context?): Context? {
        return context
    }

    @Provides
    @Singleton
    fun provideLocalePreferences(
        @ApplicationContext context: Context
    ): LocalePreferences {
        return LocalePreferences(context)
    }

}
