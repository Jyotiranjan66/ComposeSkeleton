package com.yudiz.testing.prefs

class PrefsSample(private val prefs: Prefs) {

    fun getAuthToken(): String {
        return prefs.authToken
    }
}