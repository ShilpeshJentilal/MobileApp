package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Question
import com.example.applicationsurvey.Model.Survey

class AdminCreateSurveyController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_survey)
        getSupportActionBar()?.setTitle("Create Survey")
        val User = intent.getStringExtra("Admin")
        intent.putExtra("Admin",User)
    }

    override fun onBackPressed() {
    }

    /**
     * Creates and adds a new survey to the database.
     *
     * @param view The view that was clicked.
     */
    fun createSurvey(view: View) {
        // Get the survey title from the EditText view
        val surveyTitle = findViewById<EditText>(R.id.txtBoxSurveyTitle).text.toString()

        // Get the questions from the EditText views
        val question1 = findViewById<EditText>(R.id.txtBoxQuestion1).text.toString()
        val question2 = findViewById<EditText>(R.id.txtBoxQuestion2).text.toString()
        val question3 = findViewById<EditText>(R.id.txtBoxQuestion3).text.toString()
        val question4 = findViewById<EditText>(R.id.txtBoxQuestion4).text.toString()
        val question5 = findViewById<EditText>(R.id.txtBoxQuestion5).text.toString()
        val question6 = findViewById<EditText>(R.id.txtBoxQuestion6).text.toString()
        val question7 = findViewById<EditText>(R.id.txtBoxQuestion7).text.toString()
        val question8 = findViewById<EditText>(R.id.txtBoxQuestion8).text.toString()
        val question9 = findViewById<EditText>(R.id.txtBoxQuestion9).text.toString()
        val question10 = findViewById<EditText>(R.id.txtBoxQuestion10).text.toString()

        // Check if any of the input fields are empty
        if (surveyTitle.isEmpty() || question1.isEmpty() || question2.isEmpty() || question3.isEmpty() ||
            question4.isEmpty() || question5.isEmpty() || question6.isEmpty() || question7.isEmpty() ||
            question8.isEmpty() || question9.isEmpty() || question10.isEmpty()) {

            // Show a toast message indicating that all fields must be filled
            Toast.makeText(this, "Please fill all the blank boxes", Toast.LENGTH_SHORT).show()
        } else {
            // Initialize the database helper
            val databaseHelper = DataBaseHelper(this)

            // Create a new Survey object
            val newSurvey = Survey(-1, surveyTitle)

            // Create a list of Question objects associated with the new survey
            val questions = listOf(
                Question(-1, newSurvey.Id, question1),
                Question(-1, newSurvey.Id, question2),
                Question(-1, newSurvey.Id, question3),
                Question(-1, newSurvey.Id, question4),
                Question(-1, newSurvey.Id, question5),
                Question(-1, newSurvey.Id, question6),
                Question(-1, newSurvey.Id, question7),
                Question(-1, newSurvey.Id, question8),
                Question(-1, newSurvey.Id, question9),
                Question(-1, newSurvey.Id, question10)
            )

            // Add the new survey to the database
            val surveyResult = databaseHelper.addSurvey(newSurvey)

            // Add each question to the database and collect the results
            val questionResults = questions.map { question ->
                databaseHelper.addQuestion(question, newSurvey)
            }

            // Check if the survey and all questions were added successfully
            if (surveyResult == 1 && questionResults.all { it == 1 }) {
                // Show a success toast message
                Toast.makeText(this, "New survey has been added to the database successfully", Toast.LENGTH_LONG).show()
                // Navigate to the Admin Home screen
                navigateToAdminHome()
            } else {
                // Show an error toast message
                Toast.makeText(this, "Error creating new survey", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Navigates to the Admin Home screen.
     */
    private fun navigateToAdminHome() {
        // Create an Intent to start the AdminHomeController activity
        val intent = Intent(this, AdminHomeController::class.java)
        // Retrieve the Admin username from the Intent
        val user = intent.getStringExtra("Admin")
        // Pass the Admin username to the new activity
        intent.putExtra("Admin", user)
        // Start the AdminHomeController activity
        startActivity(intent)
    }

}