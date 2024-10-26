package com.yudiz.testing.mockito

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DatabaseTest {

    private lateinit var database: Database

    @Before
    fun setup() {
        database = Database()
    }

    @Test
    fun db_getUserName() {
        Assert.assertNotEquals(null, database.getUserName(0))
    }

    @After
    fun destroy() {
//        database.release()
    }
}