package com.example.assignment

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseUser




class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var mAuthListener : AuthStateListener? = null

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("505239550692-rg9c3gteaqq5fu5agll5mm343i6ftb00.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        val googleBtn = findViewById<Button>(R.id.google)
        googleBtn.setOnClickListener {
            signIn()
        }


        val loginBtn = findViewById<Button>(R.id.loginbtn)
        loginBtn.setOnClickListener {
            GlobalScope.launch {
                login()
            }
        }

        val signupBtn = findViewById<Button>(R.id.createBtn)
        signupBtn.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, CallActivity::class.java))
        } else {
            Toast.makeText(this, "You Didnt signed in", Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private suspend fun login() {
        val email = findViewById<EditText>(R.id.emailBox).text.toString()
        val password = findViewById<EditText>(R.id.passwordBox).text.toString()

        when(Utils.validateInput(email, password)) {
            Utils.ValidCodes.SUCCESS -> {
                val userDao = (application as AssignmentApplication).database.userDao()

                withContext(Dispatchers.IO) {
                    val user = userDao.login(email, password)

                    if (user != null) {
                        val intent = Intent(this@MainActivity, CallActivity::class.java).apply {
                            putExtra("email", email)
                        }
                        startActivity(intent)
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Incorrect Email or Password",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

            Utils.ValidCodes.Missing -> {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Please enter both Email and Password", Toast.LENGTH_SHORT)
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