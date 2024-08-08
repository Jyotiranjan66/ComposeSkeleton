package com.example.data.storage

import androidx.datastore.preferences.core.stringPreferencesKey

object StorageConstants {

    object Prefs {
        const val AUTH_TOKEN = "1"
        const val USER_INFO = "2"
        const val FCM_TOKEN = "3"
        const val ROOM_KEY = "4"
    }

    object DataStore {
        val AUTH_TOKEN = stringPreferencesKey("auth")
        val ROOM_KEY = stringPreferencesKey("room")
        val FCM_TOKEN = stringPreferencesKey("fcm")
        val USER_INFO = stringPreferencesKey("user")
    }
}