package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Question
import com.example.applicationsurvey.Model.StudentSurveyRespond

class StudentSurveyQuestion1Controller : AppCompatActivity() {

    // Database helper instance
    private val db: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question1)
        supportActionBar?.title = "Question 1" // Set action bar title
        initializeQuestion()
    }

    // Function to initialize the first question
    private fun initializeQuestion() {
        val ps = intent.getStringExtra("PublishSurvey").toString()
        val questionList: ArrayList<Question> = db.getQuestions(ps)
        val questionText: String = questionList[0].QuestionText
        val questionTextView = findViewById<TextView>(R.id.txtTakeQuestion1)
        questionTextView.text = "1. $questionText"
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Finish Survey", Toast.LENGTH_LONG).show()
    }

    // Function to proceed to the next question
    fun gotoNextQuestion(view: View) {
        val radioButton1 = findViewById<RadioButton>(R.id.rdq1a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq1a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq1a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq1a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq1a5)

        val ps = intent.getStringExtra("PublishSurvey").toString()
        val questionList: ArrayList<Question> = db.getQuestions(ps)
        val questionID: String = questionList[0].Id.toString()
        val publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if (radioButton1.isChecked || radioButton2.isChecked || radioButton3.isChecked || radioButton4.isChecked || radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA1)
            val radioID: Int = radioGroup.checkedRadioButtonId
            val answerID = findViewById<RadioButton>(radioID).text.substring(0, 1)
            val studentSurveyRespond = StudentSurveyRespond(-1, studentId.toInt(), publishedSurveyID.toInt(), questionID.toInt(), answerID.toInt())

            db.addStudentSurveyRespond(studentSurveyRespond)
            val intent = Intent(this, StudentSurveyQuestion2Controller::class.java)
            intent.putExtra("StudentName", studentName)
            intent.putExtra("PublishSurvey", ps)
            intent.putExtra("PublishSurveyId", publishedSurveyID)
            intent.putExtra("StudentId", studentId)
            intent.putExtra("AnswerId", answerID)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_LONG).show()
        }
    }

    // Function to handle going to the previous question
    fun gotoPreviousQuestion(view: View) {
        // Implement going to the previous question logic here
    }
}
