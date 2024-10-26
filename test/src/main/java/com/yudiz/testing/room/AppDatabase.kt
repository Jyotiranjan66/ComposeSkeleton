package com.yudiz.testing.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yudiz.testing.room.dao.PurchaseDao
import com.yudiz.testing.room.dao.UserDao
import com.yudiz.testing.room.model.Purchase
import com.yudiz.testing.room.model.User

@Database(entities = [User::class, Purchase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
}