package com.estexis.core.ui.di

import com.estexis.core.ui.util.validation.ValidateEmailUseCase
import com.estexis.core.ui.util.validation.ValidateEmailUseCaseImpl
import com.estexis.core.ui.util.validation.ValidateFullNameUseCase
import com.estexis.core.ui.util.validation.ValidateFullNameUseCaseImpl
import com.estexis.core.ui.util.validation.ValidateNonEmptyFieldUseCase
import com.estexis.core.ui.util.validation.ValidateNonEmptyFieldUseCaseImpl
import com.estexis.core.ui.util.validation.ValidateOtpUseCase
import com.estexis.core.ui.util.validation.ValidateOtpUseCaseImpl
import com.estexis.core.ui.util.validation.ValidatePasswordUseCase
import com.estexis.core.ui.util.validation.ValidatePasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ValidationUseCaseModule {

    @Binds @Singleton
    abstract fun provideValidateEmailUseCase(impl: ValidateEmailUseCaseImpl): ValidateEmailUseCase

    @Binds @Singleton
    abstract fun provideValidatePasswordUseCase(impl: ValidatePasswordUseCaseImpl): ValidatePasswordUseCase

    @Binds @Singleton
    abstract fun provideValidateFullNameUseCase(impl: ValidateFullNameUseCaseImpl): ValidateFullNameUseCase

    @Binds @Singleton
    abstract fun provideValidateOtpUseCase(impl: ValidateOtpUseCaseImpl): ValidateOtpUseCase

    @Binds @Singleton
    abstract fun provideValidateNonEmptyFieldUseCase(impl: ValidateNonEmptyFieldUseCaseImpl): ValidateNonEmptyFieldUseCase
}
