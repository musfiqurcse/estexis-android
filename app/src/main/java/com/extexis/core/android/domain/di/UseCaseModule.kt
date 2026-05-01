package com.extexis.core.android.domain.di

import com.extexis.core.android.domain.usecases.ChangePasswordUseCase
import com.extexis.core.android.domain.usecases.ChangePasswordUseCaseImpl
import com.extexis.core.android.domain.usecases.DeleteAccountUseCase
import com.extexis.core.android.domain.usecases.DeleteAccountUseCaseImpl
import com.extexis.core.android.domain.usecases.LogOutUseCase
import com.extexis.core.android.domain.usecases.LogOutUseCaseImpl
import com.extexis.core.android.domain.usecases.LoginUseCase
import com.extexis.core.android.domain.usecases.LoginUseCaseImpl
import com.extexis.core.android.domain.usecases.RegistrationUseCase
import com.extexis.core.android.domain.usecases.RegistrationUseCaseImpl
import com.extexis.core.android.domain.usecases.SendOtpUseCase
import com.extexis.core.android.domain.usecases.SendOtpUseCaseImpl
import com.extexis.core.android.domain.usecases.UpdatePasswordUseCase
import com.extexis.core.android.domain.usecases.UpdatePasswordUseCaseImpl
import com.extexis.core.android.domain.usecases.VerifyEmailUseCase
import com.extexis.core.android.domain.usecases.VerifyEmailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun provideLoginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @Singleton
    abstract fun provideRegistrationUseCase(registrationUseCase: RegistrationUseCaseImpl): RegistrationUseCase

    @Binds
    @Singleton
    abstract fun provideVerifyEmailUseCase(verifyEmailUseCase: VerifyEmailUseCaseImpl): VerifyEmailUseCase

    @Binds
    @Singleton
    abstract fun provideSendOtpUseCase(sendOtpUseCase: SendOtpUseCaseImpl): SendOtpUseCase

    @Binds
    @Singleton
    abstract fun provideUpdatePasswordUseCase(updatePasswordUseCase: UpdatePasswordUseCaseImpl): UpdatePasswordUseCase

    @Binds
    @Singleton
    abstract fun provideLogOutUseCase(logOutUseCase: LogOutUseCaseImpl): LogOutUseCase

    @Binds
    @Singleton
    abstract fun provideChangePasswordUseCase(changePasswordUseCase: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds
    @Singleton
    abstract fun provideDeleteAccountUseCase(deleteAccountUseCase: DeleteAccountUseCaseImpl): DeleteAccountUseCase

}
