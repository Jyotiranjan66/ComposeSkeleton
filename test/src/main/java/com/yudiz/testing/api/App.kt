package com.yudiz.testing.api

import android.app.Application

open class App : Application() {
    open fun baseUrl() = "https://jsonplaceholder.typicode.com"
}