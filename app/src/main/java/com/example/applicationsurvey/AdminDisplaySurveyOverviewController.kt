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

class AdminDisplaySurveyOverviewController : AppCompatActivity() {
    private var count = 0
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_survey_states)

        db = DataBaseHelper(this)
        supportActionBar?.title = "${db.getSurveyTitlebyID(getPublishedSurvey().SurveyId)} Survey Stats"

        displaySurveyStats()
        setTotalParticipation()
    }

    /**
     * Retrieves the PublishedSurvey object passed through the intent.
     */
    private fun getPublishedSurvey(): PublishedSurvey {
        return intent.getSerializableExtra("PublishedSurvey") as PublishedSurvey
    }

    /**
     * Retrieves a list of questions for a published survey.
     * @param survey The published survey to get the list of questions for.
     * @return An ArrayList of Question objects for the published survey.
     */
    private fun getListOfQuestions(survey: PublishedSurvey): ArrayList<Question> {
        return db.getQuestions(survey.SurveyId.toString())
    }

    private fun displaySurveyStats() {
        val publishSurvey = getPublishedSurvey()
        val listOfQuestions = getListOfQuestions(publishSurvey)

        if (listOfQuestions.isEmpty()) {
            // Handle the case when there are no questions
            // For example, display a message or perform some action
            Toast.makeText(this, "No questions found for this survey", Toast.LENGTH_SHORT).show()
            return
        }

        val question = listOfQuestions[count]
        val participation = db.getTotalParticipation(publishSurvey)

        findViewById<TextView>(R.id.txtQuestionTextinDSS).text = question.QuestionText

        val percentages = getPercentages(publishSurvey, question.Id, participation)

        // Ensure that the percentages array has at least 5 elements
        if (percentages.size >= 5) {
            findViewById<EditText>(R.id.txtboxSAPercentage).setText("${percentages[0]}%")
            findViewById<EditText>(R.id.txtboxAPercentage).setText("${percentages[1]}%")
            findViewById<EditText>(R.id.txtboxNAoDPercentage).setText("${percentages[2]}%")
            findViewById<EditText>(R.id.txtboxDPercentage).setText("${percentages[3]}%")
            findViewById<EditText>(R.id.txtboxSDPercentage).setText("${percentages[4]}%")
        } else {
            // Handle the case when percentages array does not have enough elements
            // For example, display a message or perform some action
            Toast.makeText(this, "Insufficient data for percentages", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Retrieves the percentages of responses for a given question.
     * @param survey The published survey.
     * @param questionId The ID of the question.
     * @param participation The total number of participants.
     * @return An array of percentages for Strongly Agree, Agree, Neither Agree nor Disagree, Disagree, and Strongly Disagree.
     */
    private fun getPercentages(survey: PublishedSurvey, questionId: Int, participation: Int): IntArray {
        val strongAgree = ((db.getStronglyAgreePercentage(survey, questionId) / participation) * 100).toInt()
        val agree = ((db.getAgreePercentage(survey, questionId) / participation) * 100).toInt()
        val naod = ((db.getNeitherAgreeorDisagreePercentage(survey, questionId) / participation) * 100).toInt()
        val disagree = ((db.getDisagreePercentage(survey, questionId) / participation) * 100).toInt()
        val strongDisagree = ((db.getStronglyDisagreePercentage(survey, questionId) / participation) * 100).toInt()

        return intArrayOf(strongAgree, agree, naod, disagree, strongDisagree)
    }

    /**
     * Sets the total participation for a published survey in an EditText view.
     */
    private fun setTotalParticipation() {
        val participation = db.getTotalParticipation(getPublishedSurvey())
        findViewById<EditText>(R.id.txtBoxTotalParticipation).setText(participation.toString())
    }

    /**
     * Handles the click event for the next button.
     * Displays the next question and its statistics.
     * @param view The view that was clicked.
     */
    fun nextButton(view: View) {
        val listOfQuestions = getListOfQuestions(getPublishedSurvey())
        if (count + 1 < listOfQuestions.size) {
            count++
            updateQuestion() // Update the question when moving to the next one
        } else {
            Toast.makeText(this, "Sorry, no more questions", Toast.LENGTH_LONG).show()
        }
    }

    fun previousButton(view: View) {
        if (count - 1 >= 0) {
            count--
            updateQuestion() // Update the question when moving to the previous one
        } else {
            Toast.makeText(this, "Sorry, no more questions", Toast.LENGTH_LONG).show()
        }
    }


    /**
     * Updates the displayed question and its statistics based on the current count.
     */
    private fun updateQuestion() {
        val publishSurvey = getPublishedSurvey()
        val listOfQuestions = getListOfQuestions(publishSurvey)

        if (listOfQuestions.isEmpty() || count >= listOfQuestions.size) {
            // Handle the case when there are no questions or count is out of bounds
            // For example, display a message or perform some action
            Toast.makeText(this, "No questions found or count out of bounds", Toast.LENGTH_SHORT).show()
            return
        }

        val question = listOfQuestions[count]
        val participation = db.getTotalParticipation(publishSurvey)

        findViewById<TextView>(R.id.txtQuestionTextinDSS).text = question.QuestionText
        findViewById<TextView>(R.id.txtQuestioninDSSA).text = "Question ${(count + 1)}"

        val percentages = getPercentages(publishSurvey, question.Id, participation)

        // Ensure that the percentages array has at least 5 elements
        if (percentages.size >= 5) {
            findViewById<EditText>(R.id.txtboxSAPercentage).setText("${percentages[0]}%")
            findViewById<EditText>(R.id.txtboxAPercentage).setText("${percentages[1]}%")
            findViewById<EditText>(R.id.txtboxNAoDPercentage).setText("${percentages[2]}%")
            findViewById<EditText>(R.id.txtboxDPercentage).setText("${percentages[3]}%")
            findViewById<EditText>(R.id.txtboxSDPercentage).setText("${percentages[4]}%")
        } else {
            // Handle the case when percentages array does not have enough elements
            // For example, display a message or perform some action
            Toast.makeText(this, "Insufficient data for percentages", Toast.LENGTH_SHORT).show()
        }
    }

}
