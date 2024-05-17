package com.example.applicationsurvey

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.applicationsurvey.Model.Admin
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Student

class MainLoginController : AppCompatActivity() {

    /**
     * Initializes the activity.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setTitle("Survey")
        // Uncomment the line below if you want to delete expired published surveys on activity start
        // deleteExpiredPublishedSurveys()
    }

    /**
     * Redirects the user to the register page.
     */
    fun gotoRegiserPage(view: View){
        val intent = Intent(this, MainRegisterController::class.java)
        startActivity(intent)
    }

    /**
     * Deletes expired published surveys.
     * Note: This function is currently commented out. Uncomment it if needed.
     */
    fun deleteExpiredPublishedSurveys(){
        val db=DataBaseHelper(this)
        // db.deleteExpiredPublishedSurveys()
    }

    /**
     * Handles the login button click event.
     * Attempts to log in the user using the provided username and password.
     * Redirects the user to the appropriate home page based on their role (admin or student).
     */
    fun loginButton(view: View){
        val userName = findViewById<EditText>(R.id.txtBoxUsername).text.toString().lowercase()
        val userPassword = findViewById<EditText>(R.id.txtBoxPassword).text.toString()
        val message = findViewById<TextView>(R.id.txtmessage)
        val db = DataBaseHelper(this)

        if(userName.isEmpty() || userPassword.isEmpty()) {
            message.text = "Please insert Username and Password"
        } else {
            val result = db.getUser(Student(-1, userName, userPassword))
            val result2 = db.getAdminUser(Admin(-1, userName, userPassword))

            if (result == -1) {
                if (result2 == -1) {
                    message.text = "User does not exist"
                } else if (result == -2) {
                    message.text = "Cannot open database"
                } else {
                    // Admin login successful
                    Toast.makeText(this, "You logged in successfully as admin", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminHomeController::class.java)
                    intent.putExtra("Admin", userName)
                    startActivity(intent)
                }
            } else if (result == -2) {
                message.text = "Error Can't Open Database"
            } else {
                // Student login successful
                val studentId = db.getStudentID(userName).toString()
                Toast.makeText(this, "You logged in successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, StudentHomeController::class.java)
                intent.putExtra("StudentName", userName)
                intent.putExtra("StudentId", studentId)
                startActivity(intent)
            }
        }
    }
}
