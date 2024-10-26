package com.yudiz.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.yudiz.data.storage.StorageConstants.DataStore.AUTH_TOKEN
import com.yudiz.data.storage.StorageConstants.DataStore.FCM_TOKEN
import com.yudiz.data.storage.StorageConstants.DataStore.ROOM_KEY
import com.yudiz.data.storage.StorageConstants.DataStore.USER_INFO
import com.yudiz.core.model.UserDetailsModel
import kotlinx.coroutines.flow.map

class DataStoreUtil(
    private val dataStore: DataStore<Preferences>
) {

    /**
     * for synchronicity [not recommended for heavy operations]
     */
    /*var authToken: String
        get() = runBlocking {
            dataStore.data.first()[AUTH_TOKEN].orEmpty()
        }
        set(authToken) {
            runBlocking {
                dataStore.edit {
                    it[AUTH_TOKEN] = authToken
                }
            }
        }*/

    //region auth token
    fun getAuthToken() = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN].orEmpty()
    }

    suspend fun setAuthToken(value: String) {
        dataStore.edit {
            it[AUTH_TOKEN] = value
        }
    }
    //endregion

    //region room key
    fun getRoomToken() = dataStore.data
        .map { preferences ->
            preferences[ROOM_KEY].orEmpty()
        }

    suspend fun setRoomToken(value: String) {
        dataStore.edit {
            it[ROOM_KEY] = value
        }
    }
    //endregion

    //region fcm token
    fun getFcmToken() = dataStore.data
        .map { preferences ->
            preferences[FCM_TOKEN].orEmpty()
        }

    suspend fun setFcmToken(value: String) {
        dataStore.edit {
            it[FCM_TOKEN] = value
        }
    }
    //endregion

    //region user info
    fun getUserInfo() = dataStore.data
        .map { prefs ->
            prefs[USER_INFO].orEmpty()
        }

    suspend fun setUserInfo(value: UserDetailsModel) {
        dataStore.edit {
            it[USER_INFO] = Gson().toJson(value)
        }
    }
    //endregion

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }
}
