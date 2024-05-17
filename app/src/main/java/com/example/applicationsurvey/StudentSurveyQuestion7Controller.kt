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

class StudentSurveyQuestion7Controller : AppCompatActivity() {

    // Database helper instance
    private val dbHelper: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question7)

        // Get the survey ID from the intent and set the corresponding question
        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        setQuestion(surveyId)
    }

    // Set the seventh question based on the survey ID
    private fun setQuestion(surveyId:String){
        val questionList: ArrayList<Question> = dbHelper.getQuestions(surveyId)
        val question :String = questionList[6].QuestionText
        val questionTextView = findViewById<TextView>(R.id.txtTakeQuestion7)
        questionTextView.text = "7. $question"
    }

    // Proceed to the next question
    fun gotoNextQuestion(view: View){
        val radioButton1 = findViewById<RadioButton>(R.id.rdq7a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq7a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq7a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq7a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq7a5)

        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        val questionList: ArrayList<Question> = dbHelper.getQuestions(surveyId)
        val questionId: String = questionList[6].Id.toString()
        val publishedSurveyId = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if(radioButton1.isChecked || radioButton2.isChecked || radioButton3.isChecked || radioButton4.isChecked || radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA7)
            val radioId:Int = radioGroup.checkedRadioButtonId
            val answerId = findViewById<RadioButton>(radioId).text.substring(0,1)
            val studentSurveyRespond = StudentSurveyRespond(-1, studentId.toInt(), publishedSurveyId.toInt(), questionId.toInt(), answerId.toInt())
            dbHelper.addStudentSurveyRespond(studentSurveyRespond)
            val intent = Intent(this, StudentSurveyQuestion8Controller::class.java)
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
    fun gotoPreviousQuestion(view: View){
        val surveyId = intent.getStringExtra("PublishSurvey").toString()
        val publishedSurveyId = intent.getStringExtra("PublishSurveyId").toString()
        val studentId = intent.getStringExtra("StudentId").toString()

        val intent = Intent(this, StudentSurveyQuestion6Controller::class.java)
        intent.putExtra("PublishSurvey", surveyId)
        intent.putExtra("PublishSurveyId", publishedSurveyId)
        intent.putExtra("StudentId", studentId)
        startActivity(intent)
    }
}
