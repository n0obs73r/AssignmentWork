package com.example.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.database.UserEntity

@Dao
public interface UserDao {

    @Insert
    fun registerUser(userEntity: UserEntity?)

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    fun login(email: String?, password: String?): UserEntity?


}