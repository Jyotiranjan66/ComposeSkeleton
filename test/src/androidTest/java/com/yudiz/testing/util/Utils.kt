package com.yudiz.testing.util

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = CountDownLatch(1)   //waits for 1 thread to complete
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()   //completes countDownLatch
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    observeForever(observer)

    try {
        if (!latch.await(2, TimeUnit.SECONDS))  //waits for 2 second for liveData to have data
            throw TimeoutException("LiveData value was never set.")
    } finally {
        removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}