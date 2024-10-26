package com.yudiz.testing.api

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(fileName: String): String {
        try {
            val builder = StringBuilder()
            InputStreamReader(
                (InstrumentationRegistry.getInstrumentation().targetContext
                    .applicationContext as TestApp).assets.open(fileName), "UTF-8"
            ).readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}