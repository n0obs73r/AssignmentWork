package com.example.assignment

import android.app.Application
import com.example.assignment.database.UserDatabase

class AssignmentApplication : Application() {
    val database by lazy {
        UserDatabase.getDatabase(this)
    }
}