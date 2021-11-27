package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment.database.UserDao
import com.example.assignment.database.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?

    companion object {
        private const val dbName = "user"
        private var userDatabase: UserDatabase? = null
        @Synchronized
        fun getUserDatabase(context: Context?): UserDatabase? {
            if (userDatabase == null) {
                userDatabase = Room.databaseBuilder(
                    context!!,
                    UserDatabase::class.java, dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return userDatabase
        }
    }
}