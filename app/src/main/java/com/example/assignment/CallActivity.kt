package com.example.assignment


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL


class CallActivity : AppCompatActivity() {
    val button: Button? = findViewById(R.id.button)
    val join: Button? = findViewById(R.id.joinBtn)
    val code: EditText? = findViewById(R.id.codeBox)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val serverURL: URL
        try {
            serverURL = URL("https://meet.jit.si")
            val defaultOptions: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        join?.setOnClickListener(View.OnClickListener {
            val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setRoom(code?.getText().toString())
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeetActivity.launch(this@CallActivity, options)
        })
        button?.setOnClickListener(View.OnClickListener {
            finish()
            onDestroy()
            startActivity(Intent(this@CallActivity, MainActivity::class.java))
        })
    }
}