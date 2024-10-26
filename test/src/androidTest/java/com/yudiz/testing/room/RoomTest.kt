package com.yudiz.testing.room

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yudiz.testing.room.Migration.MIGRATION_1_2
import com.yudiz.testing.room.dao.UserDao
import com.yudiz.testing.room.model.User
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var context: Context
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    private val allMigrations = arrayOf(MIGRATION_1_2)

    @Rule
    @JvmField
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDao = db.userDao()
    }

    @Test
    @Throws(Exception::class)
    fun room_testInsertAndRetrieve() {
        val insertedUser = User(0, "george")

        userDao.insertAll(insertedUser)

        val retrievedUser = userDao.findByName("george")

        MatcherAssert.assertThat(retrievedUser, equalTo(insertedUser))
    }

    @Test
    @Throws(IOException::class)
    fun room_testMigrations() {
        helper.createDatabase("test", 1).apply {
            close()
        }

        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "test"
        ).addMigrations(*allMigrations).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

    @After
    @Throws(IOException::class)
    fun destroy() {
        db.close()
    }
}