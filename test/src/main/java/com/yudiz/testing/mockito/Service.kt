package com.yudiz.testing.mockito

class Service(private val database: Database) {

    fun queryUserName(userId: Int): String {
        return try {
            database.getUserName(userId)
        } catch (e: Exception) {
            e.message.orEmpty()
        }
    }

    fun queryUserNameAndProfession(userId: Int): String {
        return try {
            "${database.getUserName(userId)} ${database.getUserProfession(userId)}"
        } catch (e: Exception) {
            e.message.orEmpty()
        }
    }
}