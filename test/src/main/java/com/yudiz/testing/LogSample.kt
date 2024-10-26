package com.yudiz.testing

import timber.log.Timber

class LogSample {
    fun printLog() {
        try {
            1 / 0
        } catch (e: Exception) {
//            Log.e("TAG", "This is error")
            Timber.e("This is error")
        }
    }
}