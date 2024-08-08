package com.example.data.api

class NoInternetException(private val error: String? = null) : Exception() {
    override val message: String
        get() = error ?: "Internet not connected"
}