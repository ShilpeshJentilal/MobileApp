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

class CreateSurveyActivity : AppCompatActivity() {
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
    fun createSurvey(view: View){
        // Get the input from the EditText views
        val SurveyTitle = findViewById<EditText>(R.id.txtBoxSurveyTitle).text.toString()
        val q1 = findViewById<EditText>(R.id.txtBoxQuestion1).text.toString()
        val q2 = findViewById<EditText>(R.id.txtBoxQuestion2).text.toString()
        val q3 = findViewById<EditText>(R.id.txtBoxQuestion3).text.toString()
        val q4 = findViewById<EditText>(R.id.txtBoxQuestion4).text.toString()
        val q5 = findViewById<EditText>(R.id.txtBoxQuestion5).text.toString()
        val q6 = findViewById<EditText>(R.id.txtBoxQuestion6).text.toString()
        val q7 = findViewById<EditText>(R.id.txtBoxQuestion7).text.toString()
        val q8 = findViewById<EditText>(R.id.txtBoxQuestion8).text.toString()
        val q9 = findViewById<EditText>(R.id.txtBoxQuestion9).text.toString()
        val q10 = findViewById<EditText>(R.id.txtBoxQuestion10).text.toString()
        // Check if any of the fields are empty
        if(SurveyTitle.isEmpty()|| q1.isEmpty()|| q2.isEmpty()|| q3.isEmpty()|| q4.isEmpty()|| q5.isEmpty()|| q6.isEmpty()
            || q7.isEmpty()|| q8.isEmpty()|| q9.isEmpty()|| q10.isEmpty()){
            Toast.makeText(this,"Please fill the blank box",Toast.LENGTH_SHORT).show()
        }else {
            val mydatabase = DataBaseHelper(this)
            val newSurvey = Survey(-1, SurveyTitle)
            val newQuestion1 = Question(-1,newSurvey.Id , q1)
            val newQuestion2 = Question(-1, newSurvey.Id, q2)
            val newQuestion3 = Question(-1, newSurvey.Id, q3)
            val newQuestion4 = Question(-1, newSurvey.Id, q4)
            val newQuestion5 = Question(-1, newSurvey.Id, q5)
            val newQuestion6 = Question(-1, newSurvey.Id, q6)
            val newQuestion7 = Question(-1, newSurvey.Id, q7)
            val newQuestion8 = Question(-1, newSurvey.Id, q8)
            val newQuestion9 = Question(-1, newSurvey.Id, q9)
            val newQuestion10 = Question(-1, newSurvey.Id, q10)

            val result = mydatabase.addSurvey(newSurvey)
            val result1 = mydatabase.addQuestion(newQuestion1, newSurvey)
            val result2 = mydatabase.addQuestion(newQuestion2, newSurvey)
            val result3 = mydatabase.addQuestion(newQuestion3, newSurvey)
            val result4 = mydatabase.addQuestion(newQuestion4, newSurvey)
            val result5 = mydatabase.addQuestion(newQuestion5, newSurvey)
            val result6 = mydatabase.addQuestion(newQuestion6, newSurvey)
            val result7 = mydatabase.addQuestion(newQuestion7, newSurvey)
            val result8 = mydatabase.addQuestion(newQuestion8, newSurvey)
            val result9 = mydatabase.addQuestion(newQuestion9, newSurvey)
            val result10 = mydatabase.addQuestion(newQuestion10, newSurvey)

            when (result and result1 and result2 and result3 and result4 and result5 and result6 and result7
                    and result8 and result9 and result10){
                1 -> {
                    Toast.makeText(this, "New survey been added to the database successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminActivity::class.java)
                    val User = intent.getStringExtra("Admin")
                    intent.putExtra("Admin",User)
                    startActivity(intent)
                }
                0 ->{
                    Toast.makeText(this, "New survey been added to the database successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminActivity::class.java)
                    val User = intent.getStringExtra("Admin")
                    intent.putExtra("Admin",User)
                    startActivity(intent)
                }

                -1 -> Toast.makeText(this, "Error on creating new Survey", Toast.LENGTH_LONG).show()
            }

        }

    }
}