package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.assignment.database.UserDao
import com.example.assignment.database.UserDatabase
import com.example.assignment.database.UserEntity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val loginBtn = findViewById<Button>(R.id.loginbtn)

        loginBtn.setOnClickListener {
            login()
        }

        val signupBtn = findViewById<Button>(R.id.createBtn)
        signupBtn.setOnClickListener {
            signup()
        }

    }

    private fun signup() {
        startActivity(Intent(this@MainActivity, SignupActivity::class.java))
    }

    private fun login() {
        val emailBox = findViewById<EditText>(R.id.emailBox)
        val passwordBox = findViewById<EditText>(R.id.passwordBox)
        val userIdText: String = emailBox.text.toString()
        val passwordText: String = passwordBox.text.toString()
        startActivity(Intent(this@MainActivity, CallActivity::class.java))
        val userDatabase = UserDatabase.getUserDatabase(applicationContext)
        val userDao = userDatabase!!.userDao()
        Thread {
            val userEntity = userDao!!.login(userIdText, passwordText)
            if (userEntity == null) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Incorrect Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                startActivity(
                    Intent(
                        this@MainActivity,
                        CallActivity::class.java
                    ).putExtra("email", userIdText)
                )
            }
        }.start()
    }


}