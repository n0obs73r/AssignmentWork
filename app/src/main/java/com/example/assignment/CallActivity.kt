package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL

class CallActivity : AppCompatActivity() {

    private val serverURL = "https://meet.jit.si"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        try {
            val url = URL(serverURL)
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(url)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        val joinBtn = findViewById<Button>(R.id.joinBtn)

        joinBtn.setOnClickListener {
            val meetCode = findViewById<EditText>(R.id.codeBox).text.toString()
            val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setRoom(meetCode)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeetActivity.launch(this, options)
        }

        val endBtn = findViewById<Button>(R.id.button)
        endBtn.setOnClickListener {
            finish()
            onDestroy()
            startActivity(Intent(this@CallActivity, MainActivity::class.java))
        }
    }
}