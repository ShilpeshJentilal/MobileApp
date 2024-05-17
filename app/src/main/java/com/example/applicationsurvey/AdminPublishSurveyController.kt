package com.example.applicationsurvey

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.applicationsurvey.AdminHomeController
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import com.example.applicationsurvey.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AdminPublishSurveyController : AppCompatActivity() {

    private lateinit var db: DataBaseHelper
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_published_survey)
        supportActionBar?.title = "Publish Survey"
        db = DataBaseHelper(this)
        setupSurveySpinner()
        initializeDateFields()
    }

    private fun setupSurveySpinner() {
        val surveySpinner = findViewById<Spinner>(R.id.spnSurveyOption)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, db.getAllSurvey())
        surveySpinner.adapter = arrayAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeDateFields() {
        val currentDate = LocalDate.now()
        val startDateBox = findViewById<EditText>(R.id.txtBoxStartDate)
        val endDateBox = findViewById<EditText>(R.id.txtBoxEndDate)
        startDate = currentDate
        endDate = currentDate
        startDateBox.setText(currentDate.format(DateTimeFormatter.ISO_DATE))
        endDateBox.setText(currentDate.format(DateTimeFormatter.ISO_DATE))
    }

    fun clearStartDate(view: View) {
        findViewById<EditText>(R.id.txtBoxStartDate).text.clear()
    }

    fun clearEndDate(view: View) {
        findViewById<EditText>(R.id.txtBoxEndDate).text.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickStart(view: View) {
        showDatePicker { startDate = it }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickEnd(view: View) {
        showDatePicker { endDate = it }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker(onDateSetListener: (LocalDate) -> Unit) {
        val currentDate = LocalDate.now()
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSetListener(selectedDate)
        }
        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun publishSurvey(view: View) {
        val surveyTitle = findViewById<Spinner>(R.id.spnSurveyOption)
        if (surveyTitle.isEmpty()) {
            showToast("Please select the survey to publish")
            return
        }
        if (startDate == null || endDate == null) {
            showToast("Please select dates")
            return
        }

        if (startDate!! >= endDate!!) {
            showToast("End Date cannot be less than or equal to Start Date")
            return
        }

        val surveyId = db.getSurveyIDbyTitle(surveyTitle.selectedItem.toString())
        val existedPS = db.checkPublishedSurvey(surveyId)
        if (existedPS >= 1) {
            showToast("Selected survey is already published. Please select another survey")
            return
        }

        val newPublishSurvey = PublishedSurvey(-1, -1, startDate!!.toString(), endDate!!.toString())
        val result = db.addPublisheSurvey(newPublishSurvey, surveyTitle.selectedItem.toString())

        when (result) {
            1 -> showToast("${surveyTitle.selectedItem.toString()} has been published")
            0 -> showToast("New survey has been added to the database successfully")
            else -> showToast("Error publishing selected survey")
        }

        navigateToAdminHome()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAdminHome() {
        val intent = Intent(this, AdminHomeController::class.java)
        intent.putExtra("Admin", intent.getStringExtra("Admin"))
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Handle onBackPressed if needed
    }
}
