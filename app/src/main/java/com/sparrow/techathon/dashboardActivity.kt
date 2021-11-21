package com.sparrow.techathon

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import java.security.AccessController.getContext

class dashboardActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var sharedpreferences : SharedPreferences?= null
    private var switch:androidx.appcompat.widget.SwitchCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        switch = findViewById(R.id.toggleSwitch)

        switch!!.isChecked = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            resources.configuration.isNightModeActive
        } else {
            false
        }
//
//        val nightModeFlags: Int = applicationContext.resources.configuration.uiMode and
//                Configuration.UI_MODE_NIGHT_MASK
//        when (nightModeFlags) {
//            Configuration.UI_MODE_NIGHT_YES -> {switch!!.isChecked = true}
//            Configuration.UI_MODE_NIGHT_NO -> {switch!!.isChecked = false}
//            Configuration.UI_MODE_NIGHT_UNDEFINED -> {Toast.makeText(this,"Undefined Mode",Toast.LENGTH_SHORT).show()}
//        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        switch!!.setOnClickListener {
            if(switch!!.isChecked){
                Toast.makeText(this,"Dark mode Activated", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
                val editor = sharedpreferences!!.edit()
                editor.putString("Mode", "Night")
                editor.commit()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
                Toast.makeText(this,"Light mode Activated", Toast.LENGTH_SHORT).show()
                val editor = sharedpreferences!!.edit()
                editor.putString("Mode", "Light")
                editor.commit()
            }
        }

    }
}