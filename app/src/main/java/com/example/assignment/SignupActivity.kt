package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.database.UserDao
import com.example.assignment.database.UserDatabase
import com.example.assignment.database.UserEntity

class SignupActivity : AppCompatActivity() {
    private val emailBox: EditText = findViewById(R.id.emailEditText)
    private val passwordBox: EditText = findViewById(R.id.PasswordEditText)
    val loginBtn: Button = findViewById(R.id.login_page_btn)
    private val signupBtn: Button = findViewById(R.id.registerbtn)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupBtn.setOnClickListener {
            signup()
        }

    }

    private fun signup() {

        val userEntity = UserEntity()
        userEntity.userId = emailBox.text.toString()
        userEntity.password = passwordBox.text.toString()

        if (validateInput(userEntity)) {
            val userDatabase = UserDatabase.getUserDatabase(applicationContext)
            val userDao = userDatabase!!.userDao()
            Thread {
                userDao!!.registerUser(userEntity)
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "User Registered!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.start()
        } else {
            Toast.makeText(applicationContext, "fill all fields carefully", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun validateInput(userEntity: UserEntity): Boolean {
        return userEntity.userId == null &&
                userEntity.password == null
    }
}