package com.sparrow.techathon

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class forgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val email = findViewById<EditText>(R.id.etEmailReset)

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            if (email.text.toString().isEmpty()){
                email.setError("This field can not be blank")
            }
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Sri-1711-test", "Email sent.")
                            findViewById<TextView>(R.id.tvResultResetPassword).visibility =
                                View.VISIBLE
                            findViewById<TextView>(R.id.tvResultResetPassword).text =
                                "Email has been sent to reset your password \n Now return to Login"
                            findViewById<TextView>(R.id.tvResultResetPassword).setTextColor(Color.BLUE)
                        } else {
                            findViewById<TextView>(R.id.tvResultResetPassword).visibility =
                                View.VISIBLE
                            findViewById<TextView>(R.id.tvResultResetPassword).text = task.exception.toString().substring(task.exception.toString().indexOf(":")+2,task.exception.toString().length)
                            findViewById<TextView>(R.id.tvResultResetPassword).setTextColor(Color.RED)
                        }
                    }
            }
        }

        findViewById<Button>(R.id.tvreturn).setOnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }
    }
}