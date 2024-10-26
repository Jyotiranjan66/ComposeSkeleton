package com.yudiz.testing.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yudiz.testing.room.model.Purchase
import kotlinx.coroutines.launch

class PurchaseViewModel(private val purchasesRepo: PurchasesRepo) : ViewModel() {

    private val _purchaseData = MutableLiveData<List<Purchase>>()
    val purchaseData: LiveData<List<Purchase>> = _purchaseData

    fun savePurchase(purchase: Purchase) {
        viewModelScope.launch {
            purchasesRepo.savePurchase(purchase)
        }
    }

    fun getPurchases() {
        viewModelScope.launch {
            _purchaseData.postValue(purchasesRepo.getAllPurchases())
        }
    }
}