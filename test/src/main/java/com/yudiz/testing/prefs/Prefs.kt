package com.yudiz.testing.prefs

import android.content.Context
import android.content.SharedPreferences

open class Prefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
    private val prefsEditor: SharedPreferences.Editor = prefs.edit()

    var authToken: String
        get() = prefs.getString("token", "").orEmpty()
        set(value) {
            prefsEditor.putString("token", value)
            prefsEditor.apply()
        }

    fun clear(){
        prefsEditor.clear()
        prefsEditor.apply()
    }
}