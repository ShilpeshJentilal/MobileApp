package com.example.applicationsurvey

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import com.example.applicationsurvey.Model.Question
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class SurveyRespondGraphActivity : AppCompatActivity() {

    lateinit var pieChart: PieChart
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_respond_graph)
        var db =DataBaseHelper(this)
        val publishsurveyObj = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listofQuestions = getListofquestion(publishsurveyObj)
        val questionId = listofQuestions.get(count).Id
        val participation = db.getTotalParticipation(publishsurveyObj)
        getSupportActionBar()?.setTitle("${db.getSurveyTitlebyID(publishsurveyObj.SurveyId)} Survey Graph")

        val strongagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val disagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
        findViewById<TextView>(R.id.txtquestioninpiechart).text = listofQuestions.get(count).QuestionText


        pieChart = findViewById(R.id.pie_chart);
        setupPieChart();
        loadPieChartData(strongagree,agree,naod,disagree,sdiagree);
    }


    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Student Survey Responds"
        pieChart.setCenterTextSize(24f)
        pieChart.description.isEnabled = false
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChartData(p1:Int,p2:Int,p3:Int,p4:Int,p5:Int) {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(p1.toFloat(), "Strongly Agree"))
        entries.add(PieEntry(p2.toFloat(), "Agree"))
        entries.add(PieEntry(p3.toFloat(), "Niether Agree nor Disagree"))
        entries.add(PieEntry(p4.toFloat(), "Disagree"))
        entries.add(PieEntry(p5.toFloat(), "Strongly Disagree"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    fun getListofquestion(it:PublishedSurvey):ArrayList<Question>{
        val db = DataBaseHelper(this)
        val listofQuestions = db.getQuestions(it.Id.toString())
        return listofQuestions
    }


    fun nextButton(view: View){
        val publishsurveyObj = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listofQuestions = getListofquestion(publishsurveyObj)
        val db = DataBaseHelper(this)
        val participation = db.getTotalParticipation(publishsurveyObj)
        if(count+1 != listofQuestions.size) {
            count++
            findViewById<TextView>(R.id.txtquestioninpiechart).text = listofQuestions.get(count).QuestionText
            val questionId = listofQuestions.get(count).Id
            val oldquestionnumber = findViewById<TextView>(R.id.txtquestionoinpiechart)
            val newquestionnumber = oldquestionnumber.text.substring(0,9)+(count+1)
            findViewById<TextView>(R.id.txtquestionoinpiechart).text = newquestionnumber

            val stronglyagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val disagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()

            loadPieChartData(stronglyagree,agree,naod,disagree,sdiagree);


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
            findViewById<TextView>(R.id.txtquestioninpiechart).text = listofQuestions.get(count).QuestionText
            val questionId = listofQuestions.get(count).Id
            val oldquestionnumber = findViewById<TextView>(R.id.txtquestionoinpiechart)
            val newquestionnumber = oldquestionnumber.text.substring(0,9)+(count+1)
            findViewById<TextView>(R.id.txtquestionoinpiechart).text = newquestionnumber

            val stronglyagree =((db.getStronglyAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val agree = ((db.getAgreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val naod = ((db.getNeitherAgreeorDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val disagree = ((db.getDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()
            val sdiagree = ((db.getStronglyDisagreePercentage(publishsurveyObj,questionId)/participation)*100).toInt()

            loadPieChartData(stronglyagree,agree,naod,disagree,sdiagree);

        }else{
            Toast.makeText(this,"Sorry, No more questions", Toast.LENGTH_LONG).show()

        }
    }


}