package com.example.assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        findViewById<Button>(R.id.registerbtn).setOnClickListener {
            GlobalScope.launch {
                signup()
            }
        }

        findViewById<Button>(R.id.login_page_btn).setOnClickListener {
            finish()
        }
    }

    private suspend fun signup() {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.PasswordEditText).text.toString()

        when(Utils.validateInput(email, password)) {
            Utils.ValidCodes.SUCCESS -> {
                val newUser = UserEntity(email, password)
                val userDao = (application as AssignmentApplication).database.userDao()

                withContext(Dispatchers.IO) {
                    userDao.registerUser(newUser)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "New User Successfully Registered", Toast.LENGTH_SHORT)
                            .show()
                    }
                    finish()
                }
            }

            Utils.ValidCodes.Missing -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext, "Please enter both Email and Password", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            Utils.ValidCodes.INVALID_MAIL -> {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            Utils.ValidCodes.INVALID_PASSWORD -> {
                runOnUiThread {
                    Toast.makeText(applicationContext,"Password must be at least 8 characters", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}