package com.yudiz.testing.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yudiz.testing.room.AppDatabase
import com.yudiz.testing.room.model.Purchase
import com.yudiz.testing.util.getOrAwaitValue
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TestClass provides predefined methods like setUp, tearDown, countTestCases and many more
 */
@RunWith(AndroidJUnit4::class)
class ViewModelTest : TestCase() {

    private lateinit var purchaseViewModel: PurchaseViewModel

    /**
     * to execute tests synchronously
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() //androidx.arch.core:core-testing

    @Before
    public override fun setUp() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        purchaseViewModel = PurchaseViewModel(PurchasesRepo(db.purchaseDao()))
    }

    @Test
    fun vm_testDataInsertion() {
        val savedItem = Purchase(0, "book", 100)
        purchaseViewModel.savePurchase(savedItem)
        purchaseViewModel.getPurchases()

        val retrievedItem =
            purchaseViewModel.purchaseData.getOrAwaitValue().find { it.item == "book" }

        MatcherAssert.assertThat(retrievedItem, equalTo(savedItem))
    }

    @After
    public override fun tearDown() {
        //...
    }
}