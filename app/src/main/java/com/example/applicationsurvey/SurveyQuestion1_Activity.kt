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

class SurveyQuestion1_Activity : AppCompatActivity() {

    val db:DataBaseHelper = DataBaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question1)
        getSupportActionBar()?.setTitle("Question 1")
        var ps = intent.getStringExtra("PublishSurvey").toString()
        var publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        var StudentID = intent.getStringExtra("StudentId").toString()

        setQueston(ps)
    }

    fun setQueston(surveyID:String){
        var questionlist :ArrayList<Question> = db.getQuestions(surveyID)
        var question :String = questionlist.get(0).QuestionText
        var question1 = findViewById<TextView>(R.id.txtTakeQuestion1)
        question1.text = "1. $question"
    }


    override fun onBackPressed() {
        Toast.makeText(this,"Finish Survey", Toast.LENGTH_LONG).show()
    }

    fun gotoNextQuestion(view: View){

        val radioButton1 = findViewById<RadioButton>(R.id.rdq1a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq1a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq1a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq1a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq1a5)


        var ps = intent.getStringExtra("PublishSurvey").toString()
        var questionlist :ArrayList<Question> = db.getQuestions(ps)
        var questionID :String = questionlist.get(0).Id.toString()
        var publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        var studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if(radioButton1.isChecked||radioButton2.isChecked||radioButton3.isChecked||radioButton4.isChecked||radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA1)
            val radioID:Int = radioGroup.checkedRadioButtonId
            val answerID = findViewById<RadioButton>(radioID).text.substring(0,1)
            val StudentSurveyRespond = StudentSurveyRespond(-1,studentId.toInt(),publishedSurveyID.toInt(),questionID.toInt(),answerID.toInt())


            db.addStudentSurveyRespond(StudentSurveyRespond)
            val intent = Intent(this, SurveyQuestion2_Activity::class.java)
            intent.putExtra("StudentName",studentName)
            intent.putExtra("PublishSurvey",ps)
            intent.putExtra("PublishSurveyId",publishedSurveyID)
            intent.putExtra("StudentId",studentId)
            intent.putExtra("AnswerId",answerID)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please select an option", Toast.LENGTH_LONG).show()
        }

    }


    fun gotoPreviousQuestion(view: View){

    }









}
