package com.extexis.core.android.presentation.di

import com.extexis.core.ui.util.validation.ValidateEmailUseCase
import com.extexis.core.ui.util.validation.ValidateEmailUseCaseImpl
import com.extexis.core.ui.util.validation.ValidateFullNameUseCase
import com.extexis.core.ui.util.validation.ValidateFullNameUseCaseImpl
import com.extexis.core.ui.util.validation.ValidateNonEmptyFieldUseCase
import com.extexis.core.ui.util.validation.ValidateNonEmptyFieldUseCaseImpl
import com.extexis.core.ui.util.validation.ValidateOtpUseCase
import com.extexis.core.ui.util.validation.ValidateOtpUseCaseImpl
import com.extexis.core.ui.util.validation.ValidatePasswordUseCase
import com.extexis.core.ui.util.validation.ValidatePasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ValidationUseCaseModule {

    @Binds
    @Singleton
    abstract fun provideValidateEmailUseCase(validateEmailUseCase: ValidateEmailUseCaseImpl): ValidateEmailUseCase

    @Binds
    @Singleton
    abstract fun provideValidatePasswordUseCase(validatePasswordUseCase: ValidatePasswordUseCaseImpl): ValidatePasswordUseCase

    @Binds
    @Singleton
    abstract fun provideValidateFullNameUseCase(validateFullNameUseCase: ValidateFullNameUseCaseImpl): ValidateFullNameUseCase

    @Binds
    @Singleton
    abstract fun provideValidateOtpUseCase(validateOtpUseCase: ValidateOtpUseCaseImpl): ValidateOtpUseCase

    @Binds
    @Singleton
    abstract fun provideValidateNonEmptyFieldUseCase(validateNonEmptyFieldUseCase: ValidateNonEmptyFieldUseCaseImpl): ValidateNonEmptyFieldUseCase

}
