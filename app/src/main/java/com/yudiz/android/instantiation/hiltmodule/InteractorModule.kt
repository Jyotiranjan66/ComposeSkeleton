package com.yudiz.android.instantiation.hiltmodule

import com.yudiz.android.Interactor
import com.yudiz.android.presentation.entrymodule.datasource.LoginDataSource
import com.yudiz.core.repo.LoginRepo
import com.yudiz.core.interactor.Login
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class InteractorModule {
    @Singleton
    @Provides
    fun interactor(login: Login): Interactor = Interactor(login)

    @Singleton
    @Provides
    fun loginInteractor(loginDataSource: LoginDataSource): Login = Login(LoginRepo(loginDataSource))
}