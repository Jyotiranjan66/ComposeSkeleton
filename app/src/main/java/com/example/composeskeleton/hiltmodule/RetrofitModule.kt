package com.example.composeskeleton.hiltmodule

import androidx.multidex.BuildConfig
import com.example.data.api.HeaderHttpInterceptor
import com.example.data.api.service.EntryModuleService
import com.example.data.storage.PrefUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun getRetrofit(prefUtil: PrefUtil) = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    )
                )
                .addInterceptor(HeaderHttpInterceptor(prefUtil))
                .build()
        ).build()

    @Singleton
    @Provides
    fun getEntryApiModule(retrofit: Retrofit): EntryModuleService =
        retrofit.create(EntryModuleService::class.java)
}