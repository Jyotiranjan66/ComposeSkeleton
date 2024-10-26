package com.yudiz.testing.sample

import com.yudiz.testing.util.isEmailValid
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmailTest {
    @Test
    fun email_isValid() {
        assertEquals(true, "smoke@gmail.com".isEmailValid())
    }

    @Test
    fun email_isNotValid() {
        assertNotEquals(true, "smoke@gmailcom".isEmailValid())
    }
}