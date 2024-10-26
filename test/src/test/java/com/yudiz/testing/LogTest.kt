package com.yudiz.testing

import android.util.Log
import com.yudiz.testing.util.withTestTree
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LogTest {

    private lateinit var logSample: LogSample
    //private lateinit var testTree: TestTree

    @Before
    fun setup() {
        logSample = LogSample()
        //testTree = TestTree()
        //Timber.plant(testTree)
    }

    @Test
    fun test_log() {
        withTestTree {
            logSample.printLog()

            val lastLogEvent = logs.last()  //testTree.logs.last()

            Assert.assertEquals(lastLogEvent.message, "This is error")
            Assert.assertEquals(lastLogEvent.priority, Log.ERROR)
        }
    }

    //@After
    //fun clear(){
    //    Timber.uproot(testTree)
    //}
}