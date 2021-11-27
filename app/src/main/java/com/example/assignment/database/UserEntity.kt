package com.example.assignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "email")
    var userId: String? = null

    @ColumnInfo(name = "password")
    var password: String? = null
}

