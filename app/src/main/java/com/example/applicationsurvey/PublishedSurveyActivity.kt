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
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import java.time.LocalDateTime
import java.time.temporal.ChronoField

import java.util.*


class PublishedSurveyActivity : AppCompatActivity(){
    var DefaultDay = 0
    var DefaultMonth = 0
    var DefaultYear = 0

    var startDay = 0
    var startMonth = 0
    var startYear = 0

    var endDay = 0
    var endMonth = 0
    var endYear = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_published_survey)
        getSupportActionBar()?.setTitle("Publish Survey")
        val db = DataBaseHelper(this)
        val intent:Intent =getIntent()
        val User = intent.getStringExtra("Admin")
        intent.putExtra("Admin",User)
        val spnSurvey = findViewById<Spinner>(R.id.spnSurveyOption)
        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            db.getAllSurvey()

        )


        spnSurvey.adapter = arrayAdapter

        val currentDay = LocalDateTime.now().get(ChronoField.DAY_OF_MONTH)
        val currentMonth = LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR)
        val currentYear = LocalDateTime.now().get(ChronoField.YEAR)

        var txtStarDateBox = findViewById<EditText>(R.id.txtBoxStartDate)
        if(currentDay.toString().length==1 && currentMonth.toString().length ==1) {
            txtStarDateBox.setText("$currentYear-0$currentMonth-0$currentDay")
        }else if(currentDay.toString().length==1&& currentMonth.toString().length >1) {
            txtStarDateBox.setText("$currentYear-$currentMonth-0$currentDay")
        }else if(currentDay.toString().length>1&& currentMonth.toString().length ==1){
            txtStarDateBox.setText("$currentYear-0$currentMonth-$currentDay")

        }else{
            txtStarDateBox.setText("$currentYear-$currentMonth-$currentDay")
        }

    }

    fun clearstartDate(view: View) {
        val startdate = findViewById<EditText>(R.id.txtBoxStartDate)
        startdate.text.clear()
    }

    fun clearendDate(view: View) {
        val enddate = findViewById<EditText>(R.id.txtBoxEndDate)
        enddate.text.clear()
    }

    /**
     * Gets the current date and time from the system calendar.
     */
     fun getDateTimeCalender() {
        val cal = Calendar.getInstance()
        DefaultDay = cal.get(Calendar.DAY_OF_MONTH)
        DefaultMonth = cal.get(Calendar.MONTH)
        DefaultYear = cal.get(Calendar.YEAR)
    }

   @RequiresApi(Build.VERSION_CODES.O)
   fun setOnClickstart(view: View){
        getDateTimeCalender()
        DatePickerDialog(this,startDatePicker,Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
    }

    fun setOnClickend(view: View){
        getDateTimeCalender()
        DatePickerDialog(this,EndDatePicker,Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
   val startDatePicker = object: DatePickerDialog.OnDateSetListener{
       override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            var txtStarDateBox = findViewById<EditText>(R.id.txtBoxStartDate)
            startDay = dayOfMonth
            startMonth = month + 1
            startYear = year
            val currentDay = LocalDateTime.now().get(ChronoField.DAY_OF_MONTH)
            val currentMonth = LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR)
            val currentYear = LocalDateTime.now().get(ChronoField.YEAR)

            if(currentDay.toString().length==1 && currentMonth.toString().length ==1) {
                txtStarDateBox.setText("$currentYear-0$currentMonth-0$currentDay")
            }else if(currentDay.toString().length==1&& currentMonth.toString().length >1) {
                txtStarDateBox.setText("$currentYear-$currentMonth-0$currentDay")

            }else if(currentDay.toString().length>1&& currentMonth.toString().length ==1){
                txtStarDateBox.setText("$currentYear-0$currentMonth-$currentDay")
            }else{
                txtStarDateBox.setText("$currentYear-$currentMonth-$currentDay")
            }

        }
    }


    val EndDatePicker = object: DatePickerDialog.OnDateSetListener{
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            var txtEndDateBox = findViewById<EditText>(R.id.txtBoxEndDate)
            endDay = dayOfMonth
            endMonth = month + 1
            endYear = year

            if(endDay.toString().length==1 && endMonth.toString().length ==1) {
                txtEndDateBox.setText("$endYear-0$endMonth-0$endDay")
            }else if(endDay.toString().length==1&& endMonth.toString().length >1) {
                txtEndDateBox.setText("$endYear-$endMonth-0$endDay")
            }else if(endDay.toString().length>1&& endMonth.toString().length ==1){
                txtEndDateBox.setText("$endYear-0$endMonth-$endDay")

            }else{
                txtEndDateBox.setText("$endYear-$endMonth-$endDay")
            }
        }
    }


    override fun onBackPressed() {

    }



    @SuppressLint("SuspiciousIndentation")
    fun publishsurvey(view: View) {
        val db =DataBaseHelper(this)
        val startdate = findViewById<EditText>(R.id.txtBoxStartDate)
        val enddate = findViewById<EditText>(R.id.txtBoxEndDate)
        val surveyTitle = findViewById<Spinner>(R.id.spnSurveyOption)
        val startDay = startdate.text.toString().substring(8, 10).toInt()
        val startMonth = startdate.text.toString().substring(5, 7).toInt()
        val startYear = startdate.text.toString().substring(0, 4).toInt()


            if (surveyTitle.isEmpty()) {
                Toast.makeText(this, "Please select the survey to publish", Toast.LENGTH_SHORT).show()
            } else if (startdate.text.toString().isEmpty() || enddate.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select dates", Toast.LENGTH_SHORT).show()
             } else {
                var endDay = enddate.text.toString().substring(8, 10).toInt()
                var endMonth = enddate.text.toString().substring(5, 7).toInt()
                var endYear = enddate.text.toString().substring(0, 4).toInt()
                val surveyId = db.getSurveyIDbyTitle(surveyTitle.selectedItem.toString())
                val existedPS = db.checkPublishedSurvey(surveyId)

                if(startDay >= endDay && startMonth >= endMonth && startYear>=endYear){
                    Toast.makeText(this, "End Date cannot be less than Today's Date or Same", Toast.LENGTH_SHORT).show()
                }else if(existedPS >= 1) {
                    Toast.makeText(this, "Selected survey is Published already. Please select another Survey", Toast.LENGTH_SHORT).show()
                }else{
                    val mydatabase = DataBaseHelper(this)
                    val newpublishSurvey =
                        PublishedSurvey(-1, -1, startdate.text.toString(), enddate.text.toString())
                    val result =
                        mydatabase.addPublisheSurvey(newpublishSurvey, surveyTitle.selectedItem.toString())

                    when (result) {
                        1 -> {
                            Toast.makeText(
                                this,
                                "${surveyTitle.selectedItem.toString()} has been Published",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, AdminActivity::class.java)
                            val User = intent.getStringExtra("Admin")
                            intent.putExtra("Admin", User)
                            startActivity(intent)
                        }
                        0 -> {
                            Toast.makeText(
                                this,
                                "New survey been added to the database successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, AdminActivity::class.java)
                            val User = intent.getStringExtra("Admin")
                            intent.putExtra("Admin", User)
                            startActivity(intent)
                        }
                        -1 -> Toast.makeText(this, "Error on publishing selected survey", Toast.LENGTH_LONG)
                            .show()
                    }
                }


            }
        }



}


