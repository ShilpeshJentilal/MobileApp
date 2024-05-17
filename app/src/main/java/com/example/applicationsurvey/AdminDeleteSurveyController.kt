package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class AdminDeleteSurveyController : AppCompatActivity() {
    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentSurveyAdapterController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_survey)
        supportActionBar?.title = "Delete Survey"

        sqliteHelper = DataBaseHelper(this)

        initView()
        initRecyclerView()
        getPublishedSurveys()
        setupAdapterClickListener()
    }

    /**
     * Initializes the view elements.
     */
    private fun initView() {
        recyclerView = findViewById(R.id.rcySurveyview)
    }

    /**
     * Initializes the RecyclerView, setting its layout manager and adapter.
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentSurveyAdapterController(sqliteHelper)
        recyclerView.adapter = adapter
    }

    /**
     * Retrieves a list of published surveys from the database and adds them to the adapter for display in the RecyclerView.
     */
    private fun getPublishedSurveys() {
        val publishedSurveys = sqliteHelper.getSurveyList()
        adapter?.addItems(publishedSurveys)
    }

    /**
     * Sets up the click listener for the items in the RecyclerView.
     * When an item is clicked, a confirmation dialog is displayed to confirm the user's intention to delete the survey.
     */
    private fun setupAdapterClickListener() {
        adapter?.setOnClickItems { survey ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deleting this survey will remove all related published surveys.\n\nAre you sure you want to delete this survey?")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { dialog, _ ->
                deleteSurvey(survey.Id)
                navigateToAdminHome()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }
    }

    /**
     * Deletes the specified survey and all related entries from the database.
     *
     * @param surveyId The ID of the survey to be deleted.
     */
    private fun deleteSurvey(surveyId: Int) {
        sqliteHelper.deleteSurvey(surveyId)
        sqliteHelper.deleteQuestion(surveyId)
        sqliteHelper.deletePublishSurvey(surveyId)
    }

    /**
     * Navigates to the Admin Home screen.
     */
    private fun navigateToAdminHome() {
        val intent = Intent(this, AdminHomeController::class.java)
        val studentName = intent.getStringExtra("StudentName").toString()
        intent.putExtra("StudentName", studentName)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Prevent back press
    }
}
