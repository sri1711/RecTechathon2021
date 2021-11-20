package com.sparrow.techathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import android.text.Editable

import android.text.TextWatcher





class RegisterScreen : AppCompatActivity() {
    private var name: String? = null
    private var gender: String? = null
    private var mailID: String? = null
    private var role: String? = null
    private var pass: String? = null
    private var cpass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)
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



    }
}