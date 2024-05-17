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

class StudentSurveyQuestion10Controller : AppCompatActivity() {
    // Database helper instance
    val db: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question10)

        // Get the survey ID from the intent and set the corresponding question
        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        setQuestion(surveyId)
    }

    // Set the tenth question based on the survey ID
    fun setQuestion(surveyID: String) {
        val questionList: ArrayList<Question> = db.getQuestions(surveyID)
        val question: String = questionList.get(9).QuestionText
        val question1 = findViewById<TextView>(R.id.txtTakeQuestion10)
        question1.text = "10. $question"
    }

    // Proceed to finish the survey
    fun gotoFinishSurvey(view: View) {
        val radioButton1 = findViewById<RadioButton>(R.id.rdq10a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq10a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq10a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq10a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq10a5)

        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        val questionList: ArrayList<Question> = db.getQuestions(surveyId)
        val questionId: String = questionList.get(9).Id.toString()
        val publishedSurveyId = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if (radioButton1.isChecked || radioButton2.isChecked || radioButton3.isChecked || radioButton4.isChecked || radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA10)
            val radioID: Int = radioGroup.checkedRadioButtonId
            val answerID = findViewById<RadioButton>(radioID).text.substring(0, 1)
            val studentSurveyRespond = StudentSurveyRespond(-1, studentId.toInt(), publishedSurveyId.toInt(), questionId.toInt(), answerID.toInt())
            db.addStudentSurveyRespond(studentSurveyRespond)
            val intent = Intent(this, StudentHomeController::class.java)
            intent.putExtra("StudentName", studentName)
            intent.putExtra("PublishSurvey", surveyId)
            intent.putExtra("PublishSurveyId", publishedSurveyId)
            intent.putExtra("StudentId", studentId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_LONG).show()
        }
    }

    // Go back to the previous question
    fun gotoPreviousQuestion(view: View) {
        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        val publishedSurveyId = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()

        val intent = Intent(this, StudentSurveyQuestion9Controller::class.java)
        intent.putExtra("PublishSurvey", surveyId)
        intent.putExtra("PublishSurveyId", publishedSurveyId)
        intent.putExtra("StudentId", studentId)
        startActivity(intent)
    }
}
