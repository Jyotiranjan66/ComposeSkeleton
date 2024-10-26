package com.yudiz.testing.prefs

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.ArgumentMatchers.anyInt

import org.mockito.ArgumentMatchers.anyString


@RunWith(MockitoJUnitRunner::class)
class PrefsMockitoTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    private lateinit var prefs: Prefs
    private lateinit var prefsSample: PrefsSample

    @Before
    fun setup() {
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        `when`(context.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(
            sharedPreferencesEditor
        )

        prefs = Prefs(context)
        prefsSample = PrefsSample(prefs)
    }

    @Test
    fun prefs_testAuthToken() {
        `when`(prefs.authToken).thenReturn("test")

        Assert.assertEquals("test", prefsSample.getAuthToken())
    }
}