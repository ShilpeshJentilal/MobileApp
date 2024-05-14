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

class SurveyQuestion10_Activity : AppCompatActivity() {
    val db: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_question10)

        var ps = intent.getStringExtra("PublishSurvey").toString()
        setQueston(ps.toString())
    }

    fun setQueston(surveyID:String){
        var questionlist :ArrayList<Question> = db.getQuestions(surveyID)
        var question :String = questionlist.get(9).QuestionText
        var question1 = findViewById<TextView>(R.id.txtTakeQuestion10)
        question1.text = "10. $question"
    }


    fun gotoFinishSurvey(view: View){

        val radioButton1 = findViewById<RadioButton>(R.id.rdq10a1)
        val radioButton2 = findViewById<RadioButton>(R.id.rdq10a2)
        val radioButton3 = findViewById<RadioButton>(R.id.rdq10a3)
        val radioButton4 = findViewById<RadioButton>(R.id.rdq10a4)
        val radioButton5 = findViewById<RadioButton>(R.id.rdq10a5)

        var ps = intent.getStringExtra("PublishSurvey").toString()
        var questionlist :ArrayList<Question> = db.getQuestions(ps)
        var questionID :String = questionlist.get(9).Id.toString()
        var publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        var studentId = intent.getStringExtra("StudentId").toString()
        val studentName = intent.getStringExtra("StudentName").toString()

        if(radioButton1.isChecked||radioButton2.isChecked||radioButton3.isChecked||radioButton4.isChecked||radioButton5.isChecked) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioA10)
            val radioID:Int = radioGroup.checkedRadioButtonId
            val answerID = findViewById<RadioButton>(radioID).text.substring(0,1)
            val StudentSurveyRespond = StudentSurveyRespond(-1,studentId.toInt(),publishedSurveyID.toInt(),questionID.toInt(),answerID.toInt())
            db.addStudentSurveyRespond(StudentSurveyRespond)
            val intent = Intent(this, StudentActivity::class.java)
            intent.putExtra("StudentName",studentName)
            intent.putExtra("PublishSurvey",ps)
            intent.putExtra("PublishSurveyId",publishedSurveyID)
            intent.putExtra("StudentId",studentId)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please select an option", Toast.LENGTH_LONG).show()
        }

    }

    fun gotoPreviousQuestion(view: View){
        var ps = intent.getStringExtra("PublishSurvey").toString()

        var publishedSurveyID = intent.getStringExtra("PublishSurveyId").toString()
        var studentId = intent.getStringExtra("StudentId").toString()

        val intent = Intent(this, SurveyQuestion9_Activity::class.java)
        intent.putExtra("PublishSurvey",ps)
        intent.putExtra("PublishSurveyId",publishedSurveyID)
        intent.putExtra("StudentId",studentId)
        startActivity(intent)
    }

}