package com.sparrow.techathon

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var sharedpreferences : SharedPreferences?= null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth


        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        if(sharedpreferences!!.getString("Mode","").equals("Night")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }

//        val user = Firebase.auth.currentUser
//        if(user!=null){
//            val intent = Intent(this,dashboardActivity::class.java)
//            startActivity(intent)
//        }
//        else{
//            Log.d(TAG, "onAuthStateChanged:signed_out")
//        }


        val getStartedBtn = findViewById<Button>(R.id.get_started)

        getStartedBtn.setOnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,dashboardActivity::class.java)
            intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}