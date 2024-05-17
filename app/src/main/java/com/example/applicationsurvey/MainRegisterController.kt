package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Student

class MainRegisterController : AppCompatActivity() {

    /**
     * Initializes the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.setTitle("Register")
    }

    /**
     * Handles the click event of the save new user button.
     * Validates the input fields and saves the new user to the database.
     */
    fun saveNewUserButton(view: View){
        val loginName = findViewById<EditText>(R.id.txtBoxUsernameRegister).text.toString().lowercase()
        val password = findViewById<EditText>(R.id.txtBoxPasswordRegister).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.txtBoxConfirmPass).text.toString()
        val message = findViewById<TextView>(R.id.txtMessage)
        val pattern = Regex("[A-Za-z0-9]*")

        // Check if any field is empty
        if(loginName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            message.text = "Please all fields must be filled in"
        }
        // Check if username contains symbols
        else if (!pattern.matches(loginName)) {
            message.text = "Username must not contain symbols"
        }
        // Check if password matches the confirmation
        else if (password != confirmPassword) {
            message.text = "Password does not match"
        } else {
            // Create a new Student object with provided details
            val newUser = Student(-1, loginName, password)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addStudent(newUser)

            // Process the result of adding the student to the database
            when (result) {
                1 -> {
                    // Success: Display success message, disable button, and navigate to login page
                    Toast.makeText(this, "Your details have been added to the database successfully", Toast.LENGTH_LONG).show()
                    findViewById<Button>(R.id.btnSign_Up).isEnabled = false
                    val intent = Intent(this, MainLoginController::class.java)
                    startActivity(intent)
                }
                -1 -> message.text = "Error creating new account"
                -2 -> message.text = "Error: Cannot open database"
                -3 -> message.text = "Username already exists"
            }
        }
    }
}
