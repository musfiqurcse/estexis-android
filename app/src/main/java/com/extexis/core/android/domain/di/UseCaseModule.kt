package com.extexis.core.android.domain.di

import com.extexis.core.android.domain.usecases.ChangePasswordUseCase
import com.extexis.core.android.domain.usecases.ChangePasswordUseCaseImpl
import com.extexis.core.android.domain.usecases.DeleteAccountUseCase
import com.extexis.core.android.domain.usecases.DeleteAccountUseCaseImpl
import com.extexis.core.android.domain.usecases.LogOutUseCase
import com.extexis.core.android.domain.usecases.LogOutUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds @Singleton
    abstract fun provideLogOutUseCase(logOutUseCase: LogOutUseCaseImpl): LogOutUseCase

    @Binds @Singleton
    abstract fun provideChangePasswordUseCase(changePasswordUseCase: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds @Singleton
    abstract fun provideDeleteAccountUseCase(deleteAccountUseCase: DeleteAccountUseCaseImpl): DeleteAccountUseCase
}
