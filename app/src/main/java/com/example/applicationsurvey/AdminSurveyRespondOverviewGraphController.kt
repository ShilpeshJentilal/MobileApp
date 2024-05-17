package com.example.applicationsurvey

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class AdminSurveyRespondOverviewGraphController : AppCompatActivity() {

    lateinit var pieChart: PieChart
    var questionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_respond_graph)
        setupPieChart()
        loadQuestionData(questionIndex)
    }

    /**
     * Sets up the PieChart properties.
     */
    private fun setupPieChart() {
        pieChart = findViewById(R.id.pie_chart)
        pieChart.apply {
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.BLACK)
            centerText = "Student Survey Responds"
            setCenterTextSize(24f)
            description.isEnabled = false
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                isEnabled = true
            }
        }
    }

    /**
     * Loads data for the current question and updates the PieChart.
     */
    private fun loadQuestionData(index: Int) {
        val db = DataBaseHelper(this)
        val publishedSurvey = intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
        val listOfQuestions = db.getQuestions(publishedSurvey.SurveyId.toString())
        if (index in 0 until listOfQuestions.size) {
            val question = listOfQuestions[index]
            val participation = db.getTotalParticipation(publishedSurvey)
            findViewById<TextView>(R.id.txtquestioninpiechart).text = question.QuestionText
            findViewById<TextView>(R.id.txtquestionoinpiechart).text = "Question ${index + 1}"
            val stronglyAgree = ((db.getStronglyAgreePercentage(publishedSurvey, question.Id) / participation) * 100).toInt()
            val agree = ((db.getAgreePercentage(publishedSurvey, question.Id) / participation) * 100).toInt()
            val neitherAgreeNorDisagree = ((db.getNeitherAgreeorDisagreePercentage(publishedSurvey, question.Id) / participation) * 100).toInt()
            val disagree = ((db.getDisagreePercentage(publishedSurvey, question.Id) / participation) * 100).toInt()
            val stronglyDisagree = ((db.getStronglyDisagreePercentage(publishedSurvey, question.Id) / participation) * 100).toInt()
            loadPieChartData(stronglyAgree, agree, neitherAgreeNorDisagree, disagree, stronglyDisagree)
        } else {
            Toast.makeText(this, "No more questions", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Loads data into the PieChart.
     */
    private fun loadPieChartData(stronglyAgree: Int, agree: Int, neitherAgreeNorDisagree: Int, disagree: Int, stronglyDisagree: Int) {
        val entries = listOf(
            PieEntry(stronglyAgree.toFloat(), "Strongly Agree"),
            PieEntry(agree.toFloat(), "Agree"),
            PieEntry(neitherAgreeNorDisagree.toFloat(), "Neither Agree nor Disagree"),
            PieEntry(disagree.toFloat(), "Disagree"),
            PieEntry(stronglyDisagree.toFloat(), "Strongly Disagree")
        )
        val colors = mutableListOf<Int>().apply {
            addAll(ColorTemplate.MATERIAL_COLORS.asList())
            addAll(ColorTemplate.VORDIPLOM_COLORS.asList())
        }
        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
        }
        val data = PieData(dataSet).apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter(pieChart))
            setValueTextSize(12f)
            setValueTextColor(Color.BLACK)
        }
        pieChart.apply {
            this.data = data
            invalidate()
            animateY(1400, Easing.EaseInOutQuad)
        }
    }

    /**
     * Handles click event for the next button.
     */
    fun onNextButtonClick(view: View) {
        questionIndex++
        loadQuestionData(questionIndex)
    }

    /**
     * Handles click event for the previous button.
     */
    fun onPreviousButtonClick(view: View) {
        questionIndex--
        loadQuestionData(questionIndex)
    }
}
