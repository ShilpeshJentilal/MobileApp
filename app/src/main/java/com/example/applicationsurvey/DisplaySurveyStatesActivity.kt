package com.example.applicationsurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import com.example.applicationsurvey.Model.Question

class DisplaySurveyStatesActivity : AppCompatActivity() {


    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_survey_states)
        var db =DataBaseHelper(this)
        val publishsurveyObj = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listofQuestions = getListofquestion(publishsurveyObj)
        val questionId = listofQuestions.get(count).Id
        val participation = db.getTotalParticipation(publishsurveyObj)
        getSupportActionBar()?.setTitle("${db.getSurveyTitlebyID(publishsurveyObj.SurveyId)} Survey States")


        val strongagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val diagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()


        findViewById<EditText>(R.id.txtboxSAPercentage).setText((strongagree).toString()+"%")
        findViewById<EditText>(R.id.txtboxAPercentage).setText(agree.toString()+"%")
        findViewById<EditText>(R.id.txtboxNAoDPercentage).setText(naod.toString()+"%")
        findViewById<EditText>(R.id.txtboxDPercentage).setText(diagree.toString()+"%")
        findViewById<EditText>(R.id.txtboxSDPercentage).setText(sdiagree.toString()+"%")



        findViewById<TextView>(R.id.txtQuestionTextinDSS).text = listofQuestions.get(count).QuestionText



        setTotalParticipation(publishsurveyObj)


    }


    /**
     * Gets a list of questions for a published survey.
     * @param it The published survey to get the list of questions for.
     * @return An ArrayList of Question objects for the published survey.
     */
    fun getListofquestion(it:PublishedSurvey):ArrayList<Question>{
        val db = DataBaseHelper(this)
        val listofQuestions = db.getQuestions(it.Id.toString())
        return listofQuestions
    }



    /**
     * Sets the total participation for a published survey in an EditText view.
     * @param it The published survey to get the total participation for.
     */
    fun setTotalParticipation(it:PublishedSurvey){
        val db = DataBaseHelper(this)
        val participation = db.getTotalParticipation(it)
        val txtboxtotalparticipation = findViewById<EditText>(R.id.txtBoxTotalParticipation)
        txtboxtotalparticipation.setText(participation.toString())
    }



    fun nextButton(view: View){
        val publishsurveyObj = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listofQuestions = getListofquestion(publishsurveyObj)
        val db = DataBaseHelper(this)
        val participation = db.getTotalParticipation(publishsurveyObj)
        if(count+1 != listofQuestions.size) {
            count++
            findViewById<TextView>(R.id.txtQuestionTextinDSS).text = listofQuestions.get(count).QuestionText
            val questionId = listofQuestions.get(count).Id
            val oldquestionnumber = findViewById<TextView>(R.id.txtQuestioninDSSA)
            val newquestionnumber = oldquestionnumber.text.substring(0,9)+(count+1)
            findViewById<TextView>(R.id.txtQuestioninDSSA).text = newquestionnumber

            val stronglyagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val diagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()

            findViewById<EditText>(R.id.txtboxSAPercentage).setText((stronglyagree).toString()+"%")
            findViewById<EditText>(R.id.txtboxAPercentage).setText(agree.toString()+"%")
            findViewById<EditText>(R.id.txtboxNAoDPercentage).setText(naod.toString()+"%")
            findViewById<EditText>(R.id.txtboxDPercentage).setText(diagree.toString()+"%")
            findViewById<EditText>(R.id.txtboxSDPercentage).setText(sdiagree.toString()+"%")


        }else{
            Toast.makeText(this,"Sorry, No more questions", Toast.LENGTH_LONG).show()

        }

    }


    fun previousButton(view: View){
        val publishsurveyObj = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listofQuestions = getListofquestion(publishsurveyObj)
        val db = DataBaseHelper(this)
        val participation = db.getTotalParticipation(publishsurveyObj)
        if(count-1 >=0){
            count--
            val questionId = listofQuestions.get(count).Id
            findViewById<TextView>(R.id.txtQuestionTextinDSS).text = listofQuestions.get(count).QuestionText
            val oldquestionnumber = findViewById<TextView>(R.id.txtQuestioninDSSA)
            val newquestionnumber = oldquestionnumber.text.substring(0,9)+(count+1)
            findViewById<TextView>(R.id.txtQuestioninDSSA).text = newquestionnumber

            val stronglyagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val diagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()


            findViewById<EditText>(R.id.txtboxSAPercentage).setText((stronglyagree).toString()+"%")
            findViewById<EditText>(R.id.txtboxAPercentage).setText(agree.toString()+"%")
            findViewById<EditText>(R.id.txtboxNAoDPercentage).setText(naod.toString()+"%")
            findViewById<EditText>(R.id.txtboxDPercentage).setText(diagree.toString()+"%")
            findViewById<EditText>(R.id.txtboxSDPercentage).setText(sdiagree.toString()+"%")
        }else{
            Toast.makeText(this,"Sorry, No more questions", Toast.LENGTH_LONG).show()

        }
    }


}