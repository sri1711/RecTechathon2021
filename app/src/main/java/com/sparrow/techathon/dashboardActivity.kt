package com.sparrow.techathon

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.AccessController.getContext

class dashboardActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var SWITCHPREFERENCES = "switchPrefs"
    private var sharedpreferences : SharedPreferences?= null
    private var sharedpreferencesswitch : SharedPreferences?= null
    private var switch:androidx.appcompat.widget.SwitchCompat? = null
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        switch = findViewById(R.id.toggleSwitch)
        sharedpreferencesswitch = getSharedPreferences(SWITCHPREFERENCES, MODE_PRIVATE)
        switch!!.isChecked = sharedpreferencesswitch!!.getBoolean("Check",false).equals(true)

        if(!resources.configuration.isNightModeActive){
            switch!!.isChecked = true
        }

        findViewById<ImageButton>(R.id.ibtn_logout).setOnClickListener {
            Firebase.auth.signOut()
            val editor = sharedpreferences!!.edit()
            editor.clear().commit()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
            val editor2 = sharedpreferencesswitch!!.edit()
            editor2.clear().commit()
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }



        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        sharedpreferencesswitch = getSharedPreferences(SWITCHPREFERENCES, MODE_PRIVATE)
        switch!!.setOnClickListener {
            if(switch!!.isChecked){
                Toast.makeText(this,"Dark mode Activated", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
                val editor = sharedpreferences!!.edit()
                val switchEditor = sharedpreferencesswitch!!.edit()
                editor.putString("Mode", "Night")
                switchEditor.putBoolean("Check",true)
                editor.commit()
                switchEditor.commit()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
                Toast.makeText(this,"Light mode Activated", Toast.LENGTH_SHORT).show()
                val editor = sharedpreferences!!.edit()
                val switchEditor = sharedpreferencesswitch!!.edit()
                editor.putString("Mode", "Light")
                switchEditor.putBoolean("Check",false)
                editor.commit()
                switchEditor.commit()
            }
        }

    }
}