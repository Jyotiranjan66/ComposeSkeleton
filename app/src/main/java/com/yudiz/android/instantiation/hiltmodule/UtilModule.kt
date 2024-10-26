package com.yudiz.android.instantiation.hiltmodule

import com.yudiz.android.presentation.util.ToastUtil
import com.yudiz.android.util.SecurityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UtilModule {
    @Singleton
    @Provides
    fun security() = SecurityUtil()

    @Singleton
    @Provides
    fun toast() = ToastUtil()
}