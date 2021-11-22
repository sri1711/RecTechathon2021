package com.sparrow.techathon

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.security.AccessController.getContext

class dashboardActivity : AppCompatActivity() {
    private var MyPREFERENCES = "MyPrefs"
    private var SWITCHPREFERENCES = "switchPrefs"
    private var sharedpreferences : SharedPreferences?= null
    private var sharedpreferencesswitch : SharedPreferences?= null
    private var switch:androidx.appcompat.widget.SwitchCompat? = null
    var profileImageUri : String? = null
    var profileName : String? = null
    var profileGender : String? = null
    var profileEmail : String? = null
    var profileRole : String? = null
    var profileID : String? = null
    var currentUser : user? = null
    private lateinit var mProgress : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mProgress = ProgressDialog(this)
        mProgress!!.setCancelable(false)
        mProgress!!.setTitle("Fetching your Data")
        mProgress!!.setMessage("Please Wait..")
        mProgress!!.isIndeterminate = true
        mProgress!!.show()
        switch = findViewById(R.id.toggleSwitch)
        sharedpreferencesswitch = getSharedPreferences(SWITCHPREFERENCES, MODE_PRIVATE)
        switch!!.isChecked = sharedpreferencesswitch!!.getBoolean("Check",false).equals(true)


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

        Log.i("SRI171",Firebase.auth.currentUser!!.uid.toString())

        profileID = Firebase.auth.currentUser!!.uid.toString()
        setProfileDetails()

    }

    private fun injectProfileDetailsToView() {
        val iv_user_profileImage = findViewById<ImageView>(R.id.iv_user_profileImage)
        val tv_userName = findViewById<TextView>(R.id.tv_userName)
        val tv_email = findViewById<TextView>(R.id.tv_email)
        val tv_role = findViewById<TextView>(R.id.tv_role)

        tv_userName.text = profileName
        tv_email.text = profileEmail
        tv_role.text = profileRole
        if(profileGender.equals("Male")){
            iv_user_profileImage.setBackgroundResource(R.drawable.man)
        }
        else if(profileGender.equals("Female")){
            iv_user_profileImage.setBackgroundResource(R.drawable.girl)
        }
        else{
            iv_user_profileImage.setBackgroundResource(R.drawable.user)
        }
        mProgress.dismiss()
    }

    private fun setProfileDetails() {
        val myref = FirebaseDatabase.getInstance().getReference("users/$profileID")
        myref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(user::class.java)
                Log.i("SKHST_1635","Successfully retrieved ${currentUser?.Name} from database!")
                profileName = currentUser?.Name
                profileEmail = currentUser?.Email
                profileGender = currentUser?.Gender
                profileRole = currentUser?.Designation

                injectProfileDetailsToView()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("SKHST_1635","Failed to retrieve user from the database!")
            }
        })

    }
}