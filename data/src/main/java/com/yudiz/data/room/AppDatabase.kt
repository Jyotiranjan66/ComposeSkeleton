package com.yudiz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yudiz.data.room.dao.UserDao
import com.yudiz.data.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doorDao(): UserDao
}