package com.example.composeskeleton.hiltmodule

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.data.room.AppDatabase
import com.example.data.storage.DataStoreUtil
import com.example.data.storage.PrefUtil
import com.example.data.storage.StorageConstants.Prefs.ROOM_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton
import kotlin.random.Random

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context, prefUtil: PrefUtil): AppDatabase {
        var roomKey = ""
        if (!prefUtil.hasKey(ROOM_KEY))
            roomKey = Random.nextDouble().toString()
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "room-db"
        ).openHelperFactory(
            SupportFactory(
                SQLiteDatabase.getBytes(
                    roomKey.toCharArray()
                )
            )
        ).build()
    }

    @Singleton
    @Provides
    fun prefs(@ApplicationContext context: Context): PrefUtil = PrefUtil(context)

    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStoreUtil = DataStoreUtil(
        preferencesDataStore(name = "data-store").getValue(
            context,
            String::javaClass
        )
    )
}