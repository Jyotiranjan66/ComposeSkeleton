package com.yudiz.testing.mockito

open class Database {

    @Throws
    open fun getUserName(userId: Int): String {
        println("sumeet")
        return if (userId == 1) "Smoke" else "Sumeet"
    }

    @Throws
    open fun getUserProfession(userId: Int): String {
        return if (userId == 1) "Developer" else "Advocate"
    }
}