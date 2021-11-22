package com.sparrow.techathon

import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var sharedpreferences : SharedPreferences?= null
    var shortAnimationDuration : Int = 3000
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth


//---------------------------Animation------------------
        Handler().postDelayed({
            if(auth.currentUser != null){
                val intent = Intent(this,dashboardActivity::class.java)
                intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            else{
                startAnimation()
            }
        },3000 )
        //-----------------------------------------------

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)

            if(sharedpreferences!!.getString("Mode","").equals("Night")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }



        val getStartedBtn = findViewById<Button>(R.id.get_started)

        getStartedBtn.setOnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun startAnimation(){
        val Logo = findViewById<LinearLayout>(R.id.lottieWelcomeAnimation)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        ObjectAnimator.ofFloat(Logo, "translationY", -(height.toFloat()/4)).apply {
            duration = 2000
            start()
        }
        Handler().postDelayed({


            crossfadeText()
            Handler().postDelayed({
                crossfadeButton()
            },2000)


        },2000)
    }

    private fun crossfadeText() {
        findViewById<LinearLayout>(R.id.Content).apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

    private fun crossfadeButton() {
        findViewById<MaterialButton>(R.id.get_started).apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

}