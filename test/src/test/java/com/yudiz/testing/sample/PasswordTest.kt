package com.yudiz.testing.sample

import com.yudiz.testing.util.isPasswordValid
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PasswordTest {
    @Test
    fun password_isValid() {
        assertEquals(true, "abcde".isPasswordValid())
    }

    @Test
    fun password_isNotValid() {
        assertNotEquals(true, "a".isPasswordValid())
    }
}