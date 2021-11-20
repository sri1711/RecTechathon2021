package com.sparrow.techathon

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var googleSignInClient : GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        val mbtn_signIn = findViewById<com.google.android.material.button.MaterialButton>(R.id.mbtn_signIn)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        mbtn_signIn.setOnClickListener {
            val email = findViewById<EditText>(R.id.etUserId)
            val password = findViewById<EditText>(R.id.etPassword)
            if (email.text.toString().isEmpty()){
                email.setError("This field can not be blank")
            }
            else if(!(email.text.contains("@")) && !(email.text.contains("."))){
                email.setError("Enter a proper mail Id")
            }
            else{
                if (password.text.toString().isEmpty()){
                    password.setError("This field can not be blank")
                }
                else{
                    //mProgress!!.show()
                    validateLogin(email.text.toString(), password.text.toString())
                }
            }

        }

        auth = Firebase.auth





    }

    private fun validateLogin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                   // mProgress!!.dismiss()
                    val user = auth.currentUser
                    val intent = Intent(this,dashboardActivity::class.java)
                    intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
//                    if(user!!.isEmailVerified) {
//                        val intent = Intent(this,dashboardActivity::class.java)
//                        intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        startActivity(intent)
//                    }
//                    else{
//                        Toast.makeText(this,"Email not Verified!!", Toast.LENGTH_SHORT).show()
//                    }
                } else {
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    //mProgress!!.dismiss()
                    Toast.makeText(
                        baseContext, "Authentication failed \n ${task.exception.toString().substring(task.exception.toString().indexOf(":")+2,task.exception.toString().length)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
//            val intent = Intent(this,dashboardActivity::class.java)
//            intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
        }
    }
}