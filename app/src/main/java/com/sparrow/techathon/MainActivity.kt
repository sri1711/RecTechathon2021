package com.sparrow.techathon

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var sharedpreferences : SharedPreferences?= null
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth


//        val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        when (currentNightMode) {
//            Configuration.UI_MODE_NIGHT_NO -> {} // Night mode is not active, we're using the light theme
//            Configuration.UI_MODE_NIGHT_YES -> {} // Night mode is active, we're using dark theme
//        }

        if(!resources.configuration.isNightModeActive){
            sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
            if(sharedpreferences!!.getString("Mode","").equals("Night")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
        }
        val getStartedBtn = findViewById<Button>(R.id.get_started)

        getStartedBtn.setOnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
////            val intent = Intent(this,dashboardActivity::class.java)
////            intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
////            startActivity(intent)
////            finish()
//        }
//    }
}