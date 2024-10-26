package com.yudiz.data.room.dao

import androidx.room.*
import com.yudiz.data.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAll(): List<UserEntity>

//    @Query("SELECT * FROM log_table WHERE title LIKE :title")
//    fun findByTitle(title: String): TodoEntity

    @Insert
    fun insertAll(vararg userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Update
    fun update(vararg userEntity: UserEntity)
}