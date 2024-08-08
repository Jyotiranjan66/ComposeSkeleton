package com.example.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data.storage.StorageConstants.DataStore.AUTH_TOKEN
import com.example.data.storage.StorageConstants.DataStore.ROOM_KEY
import com.google.gson.Gson
import kotlinx.coroutines.flow.map

class DataStoreUtil(
    private val dataStore: DataStore<Preferences>
) {
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

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }
}