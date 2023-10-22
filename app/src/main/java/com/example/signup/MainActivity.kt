package com.example.signup

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var birthdayEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        maleRadioButton = findViewById(R.id.maleRadioButton)
        femaleRadioButton = findViewById(R.id.femaleRadioButton)
        birthdayEditText = findViewById(R.id.birthdayEditText)
        addressEditText = findViewById(R.id.addressEditText)
        emailEditText = findViewById(R.id.emailEditText)
        termsCheckBox = findViewById(R.id.termsCheckBox)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            if (validateForm() && termsCheckBox.isChecked) {
                val firstName = firstNameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val gender = if (maleRadioButton.isChecked) "Male" else "Female"
                val birthday = birthdayEditText.text.toString()
                val address = addressEditText.text.toString()
                val email = emailEditText.text.toString()

                println("First Name: $firstName")
                println("Last Name: $lastName")
                println("Gender: $gender")
                println("Birthday: $birthday")
                println("Address: $address")
                println("Email: $email")
            } else {
                Toast.makeText(this, "Please fill in all fields, provide a valid email address, and agree to the Terms of Use.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun validateForm(): Boolean {
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val birthday = birthdayEditText.text.toString()
        val address = addressEditText.text.toString()
        val email = emailEditText.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || address.isEmpty() || !isEmailValid(email)) {
            Toast.makeText(this, "Please fill in all fields or provide a valid email address.", Toast.LENGTH_SHORT).show()
            return false
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        try {
            val parsedDate = dateFormat.parse(birthday)
            val currentDate = Date()
            if (parsedDate == null || parsedDate.after(currentDate)) {
                Toast.makeText(this, "Invalid birthday format or date.", Toast.LENGTH_SHORT).show()
                return false
            }
        } catch (e: ParseException) {
            Toast.makeText(this, "Invalid birthday format. Use dd/MM/yyyy.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!maleRadioButton.isChecked && !femaleRadioButton.isChecked) {
            Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
