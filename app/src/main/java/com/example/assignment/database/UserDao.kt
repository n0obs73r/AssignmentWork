package com.example.assignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun registerUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    fun login(email: String, password: String): UserEntity?
}