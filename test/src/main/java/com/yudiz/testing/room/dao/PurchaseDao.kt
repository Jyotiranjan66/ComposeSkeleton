package com.yudiz.testing.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yudiz.testing.room.model.Purchase

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchase")
    suspend fun getAll(): List<Purchase>

    @Insert
    suspend fun insertAll(vararg purchases: Purchase)

    @Delete
    suspend fun delete(purchase: Purchase)
}