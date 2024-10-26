package com.yudiz.testing.prefs

import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PrefsTest {

    private lateinit var prefs: Prefs
    private lateinit var prefsSample: PrefsSample

    @Before
    fun setup() {
        prefs = Prefs(SPMockBuilder().createContext())
        prefsSample = PrefsSample(prefs)
    }

    @Test
    fun prefs_testAuthToken() {
        prefs.authToken = "test"
        Assert.assertEquals("test", prefsSample.getAuthToken())
    }

    @Test
    fun prefs_testClear() {
        prefs.authToken = "test"
        prefs.clear()
        Assert.assertEquals("", prefsSample.getAuthToken())
    }
}