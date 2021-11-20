package com.sparrow.techathon

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.textfield.TextInputLayout
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterScreen : AppCompatActivity() {
    private var gender: String?= null
    private var role: String? = null
    private lateinit var auth: FirebaseAuth
    private var rootNode: FirebaseDatabase? = null
    private var referenceDatabase: DatabaseReference? = null
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)
        val name = findViewById<EditText>(R.id.etName)
        val mailID = findViewById<EditText>(R.id.et_Mail)
        val pass = findViewById<EditText>(R.id.et_pass)
        val cpass = findViewById<EditText>(R.id.et_cpass)
        // Initialize Firebase Auth
        auth = Firebase.auth

        val genderLayout = findViewById<TextInputLayout>(R.id.GenderLayout)

        val genderType = arrayOf("Male", "Female", "Rather Not say")

        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            genderType
        )

        val editTextFilledExposedDropdown =
            findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter)

        editTextFilledExposedDropdown.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                Toast.makeText(applicationContext,s.toString(),Toast.LENGTH_SHORT).show()
                gender = s.toString()
                if(s.toString() == "Male"){
                    findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.genderImage).setBackgroundResource(R.drawable.man)
                }
                if(s.toString() == "Female"){
                    findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.genderImage).setBackgroundResource(R.drawable.girl)
                }
                if(s.toString() == "Rather Not say"){
                    findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.genderImage).setBackgroundResource(R.drawable.user)
                }
            }
        })
        val designation = arrayOf("System Administrator","UI/UX Developer", "Software Developer", "Program Analyst","System Architect")
        val designationAdapter = ArrayAdapter(this,R.layout.dropdown_menu_popup_item,designation)
        val designationTextFilledExposedDropdown = findViewById<AutoCompleteTextView>(R.id.designation_exposed_dropdown)
        designationTextFilledExposedDropdown.setAdapter(designationAdapter)

        designationTextFilledExposedDropdown.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                Toast.makeText(applicationContext,s.toString(),Toast.LENGTH_SHORT).show()
                role = s.toString()
            }
        })

        name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! <5){
                    name.error = "Name should have more than 5 characters"
                }
            }

        })

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{2,4}+".toRegex()
        mailID.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!(s.toString().trim().matches(emailPattern) && s?.length!!>0)){
                    mailID.error = "Mail ID is invalid"
                }
            }

        })
        val passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$".toRegex()




        findViewById<Button>(R.id.btn_signup).setOnClickListener {
            if(designationTextFilledExposedDropdown.text.isEmpty() || editTextFilledExposedDropdown.text.isEmpty()
                || pass.text.isEmpty() ||cpass.text.isEmpty() || name.text.isEmpty() || mailID.text.isEmpty()){
                Toast.makeText(this,"Please fill the details",Toast.LENGTH_SHORT).show()
            }
            if(name.error!=null){
                Toast.makeText(this,"Please enter the valid name",Toast.LENGTH_SHORT).show()
            }
            if(mailID.error!=null){
                Toast.makeText(this,"Please enter valid Email",Toast.LENGTH_SHORT).show()
            }
            if(!(pass.text.toString().matches(passPattern))){
                Toast.makeText(this,"Password is weak :(",Toast.LENGTH_SHORT).show()
            }
            if(cpass.text.toString() != pass.text.toString()){
                Toast.makeText(this,"Password doesn't match with confirm password",Toast.LENGTH_SHORT).show()
            }
            else{
                performRegister(name.text.toString(),mailID.text.toString(),pass.text.toString(),role!!,gender!!)
                Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun performRegister(name: String,email: String, password: String,role: String,gender: String) {
        rootNode = FirebaseDatabase.getInstance("https://techathon2021-ef8ff-default-rtdb.asia-southeast1.firebasedatabase.app")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    userID = task.result?.user?.uid.toString()
                    referenceDatabase = rootNode!!.reference.child("users/${userID}")
                    referenceDatabase!!.child("Name").setValue(name)
                    referenceDatabase!!.child("Gender").setValue(gender)
                    referenceDatabase!!.child("Designation").setValue(role)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}