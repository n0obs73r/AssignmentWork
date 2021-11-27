package com.example.assignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey @ColumnInfo(name = "email")
    var userID: String,

    @ColumnInfo(name = "password")
    var password: String
)

