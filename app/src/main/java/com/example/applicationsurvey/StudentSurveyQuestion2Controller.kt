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

class StudentSurveyQuestion2Controller : AppCompatActivity() {

    private val db: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question2)

        // Retrieve survey ID from intent and set the corresponding question
        val ps = intent.getStringExtra("PublishSurvey").toString()
        setQuestion(ps)
    }

    // Function to set the second question based on the survey ID
    private fun setQuestion(surveyID: String) {
        val questionList: ArrayList<Question> = db.getQuestions(surveyID)
        val questionText: String = questionList[1].QuestionText
        val questionTextView = findViewById<TextView>(R.id.txtTakeQuestion2)
        questionTextView.text = "2. $questionText"
    }

    // Function to proceed to the next question
    fun gotoNextQuestion(view: View) {
        val radioButton1 = findViewById<RadioButton>(R.id.rdq2a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq2a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq2a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq2a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq2a5)

        val ps = intent.getStringExtra("PublishSurvey").toString()
        val questionList: ArrayList<Question> = db.getQuestions(ps)
        val questionID: String = questionList[1].Id.toString()
        val publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if (radioButton1.isChecked || radioButton2.isChecked || radioButton3.isChecked || radioButton4.isChecked || radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA2)
            val radioID: Int = radioGroup.checkedRadioButtonId
            val answerID = findViewById<RadioButton>(radioID).text.substring(0, 1)
            val studentSurveyRespond = StudentSurveyRespond(-1, studentId.toInt(), publishedSurveyID.toInt(), questionID.toInt(), answerID.toInt())
            db.addStudentSurveyRespond(studentSurveyRespond)
            val intent = Intent(this, StudentSurveyQuestion3Controller::class.java)
            intent.putExtra("StudentName", studentName)
            intent.putExtra("PublishSurvey", ps)
            intent.putExtra("PublishSurveyId", publishedSurveyID)
            intent.putExtra("StudentId", studentId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_LONG).show()
        }
    }

    // Function to go back to the previous question
    fun gotoPreviousQuestion(view: View) {
        val ps = intent.getStringExtra("PublishSurvey").toString()
        val publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()

        val intent = Intent(this, StudentSurveyQuestion1Controller::class.java)
        intent.putExtra("PublishSurvey", ps)
        intent.putExtra("PublishSurveyId", publishedSurveyID)
        intent.putExtra("StudentId", studentId)
        startActivity(intent)
    }
}
