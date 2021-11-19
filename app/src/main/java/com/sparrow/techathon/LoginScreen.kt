package com.sparrow.techathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        val mbtn_signIn = findViewById<com.google.android.material.button.MaterialButton>(R.id.mbtn_signIn)
        mbtn_signIn.setOnClickListener {
            val intent = Intent(this,dashboardActivity::class.java)
            intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}