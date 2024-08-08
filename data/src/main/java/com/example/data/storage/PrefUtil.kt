package com.example.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.data.storage.StorageConstants.Prefs.AUTH_TOKEN
import com.example.data.storage.StorageConstants.Prefs.ROOM_KEY
import com.example.data.storage.StorageConstants.Prefs.USER_INFO
import com.example.data.util.convertToModel
import com.example.domain.model.UserDetailsModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PrefUtil(context: Context) {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val prefEditor: SharedPreferences.Editor = prefs.edit()

    var authToken: String?
        get() = prefs.getString(AUTH_TOKEN, "")
        set(authToken) {
            prefEditor.putString(AUTH_TOKEN, authToken)
            prefEditor.apply()
        }

    var roomKey: String
        get() = prefs.getString(ROOM_KEY, "").orEmpty()
        set(key) {
            prefEditor.putString(ROOM_KEY, key)
            prefEditor.apply()
        }

    var userInfo: UserDetailsModel?
        get() = prefs.getString(USER_INFO, "")?.convertToModel()
        set(data) {
            prefEditor.putString(USER_INFO, Json.encodeToString(data))
            prefEditor.apply()
        }

    fun hasKey(key: String) = prefs.contains(key)

    fun clearPrefs() {
        prefs.all.forEach {
            prefEditor.remove(it.key)
        }
        prefEditor.apply()
    }
}

