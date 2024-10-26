package com.yudiz.testing.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpProvider {

    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient {
        return if (okHttpClient == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()
            this.okHttpClient = okHttpClient
            okHttpClient
        } else
            okHttpClient!!
    }
}