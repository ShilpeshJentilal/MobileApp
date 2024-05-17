package com.example.applicationsurvey

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import java.util.*

class AdminEditPublishedSurveyController : AppCompatActivity() {
    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: AdminEditPublishedSurveyAdapterController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_published_survey)
        supportActionBar?.title = "Edit/ Remove Published Survey"

        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()
        adapter?.setOnClickDelete { delete(it) }
        adapter?.setOnClickEdit { edit(it) }
    }

    /**
     * Initializes the RecyclerView view.
     */
    private fun initView() {
        recyclerView = findViewById(R.id.rcyEditPs)
    }

    /**
     * Initializes the RecyclerView with a layout manager and adapter.
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminEditPublishedSurveyAdapterController(sqliteHelper)
        recyclerView.adapter = adapter
    }

    /**
     * Fetches the list of published surveys from the database and updates the adapter.
     */
    private fun getPublishedSurveys() {
        val psList = sqliteHelper.getPublishedSurvey()
        adapter?.addItems(psList)
    }

    /**
     * Prevents the back button from functioning.
     */
    override fun onBackPressed() {
        // Do nothing on back pressed
    }

    /**
     * Deletes a Published Survey object from the database.
     * @param survey The Published Survey object to be deleted.
     */
    private fun delete(survey: PublishedSurvey) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Deleting this Published Survey will remove it from the data.\n\nAre you sure you want to delete this Published Survey?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                sqliteHelper.deletePublishSurvey(survey.SurveyId)
                Toast.makeText(this, "Successfully deleted", Toast.LENGTH_LONG).show()
                reloadActivity()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    /**
     * Edits a published survey by updating its end date.
     * @param survey The published survey to be edited.
     */
    private fun edit(survey: PublishedSurvey) {
        val surveyTitle = sqliteHelper.getSurveyTitlebyID(survey.SurveyId)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.lo_editpublishsurvey, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
            .setTitle("Update $surveyTitle")
        val alertDialog = builder.show()

        val btnCalendar = dialogView.findViewById<Button>(R.id.btnEditDate)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnUpdate = dialogView.findViewById<Button>(R.id.btnUpdate)
        val moduleTitle = dialogView.findViewById<EditText>(R.id.txtboxeditmodule)
        val startDate = dialogView.findViewById<EditText>(R.id.txtBoxstart)
        val endDate = dialogView.findViewById<EditText>(R.id.txtBoxend)

        moduleTitle.setText(surveyTitle)
        startDate.setText(survey.StartDate)
        endDate.setText(survey.EndDate)

        btnCalendar.setOnClickListener {
            showDatePickerDialog(endDate)
        }

        btnUpdate.setOnClickListener {
            if (isEndDateValid(startDate.text.toString(), endDate.text.toString())) {
                val updatedSurvey = PublishedSurvey(survey.Id, survey.SurveyId, survey.StartDate, endDate.text.toString())
                sqliteHelper.updatePublishedSurvey(updatedSurvey)
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
                reloadActivity()
            } else {
                Toast.makeText(this, "End Date cannot be before or the same as the Start Date", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    /**
     * Shows a DatePickerDialog for selecting an end date.
     * @param endDateEditText The EditText where the selected date will be displayed.
     */
    private fun showDatePickerDialog(endDateEditText: EditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            endDateEditText.setText(formattedDate)
        }
        DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    /**
     * Validates if the end date is after the start date.
     * @param startDate The start date in String format.
     * @param endDate The end date in String format.
     * @return True if the end date is valid, false otherwise.
     */
    private fun isEndDateValid(startDate: String, endDate: String): Boolean {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val start = sdf.parse(startDate)
            val end = sdf.parse(endDate)
            end.after(start)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Reloads the current activity.
     */
    private fun reloadActivity() {
        val intent = Intent(this, AdminEditPublishedSurveyController::class.java)
        val studentName = intent.getStringExtra("StudentName").toString()
        intent.putExtra("StudentName", studentName)
        startActivity(intent)
    }
}
