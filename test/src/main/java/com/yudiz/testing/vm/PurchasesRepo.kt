package com.yudiz.testing.vm

import com.yudiz.testing.room.dao.PurchaseDao
import com.yudiz.testing.room.model.Purchase

class PurchasesRepo(private val purchaseDao: PurchaseDao) {

    suspend fun savePurchase(purchase: Purchase) {
        purchaseDao.insertAll(purchase)
    }

    suspend fun getAllPurchases() = purchaseDao.getAll()
}