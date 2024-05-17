package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class StudentHomeController : AppCompatActivity() {

    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: AdminPublishSurveyAdapterController? = null

    /**
     * Initializes the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        getSupportActionBar()?.setTitle("Student HomePage")

        // Display welcome message with the student's username
        val welcome = findViewById<TextView>(R.id.txtwelcomeStudent)
        val intent: Intent = getIntent()
        val username = intent.getStringExtra("StudentName").toString()
        welcome.text = "Welcome $username".toUpperCase()
        val studentID = intent.getStringExtra("StudentId").toString()

        // Initialize database helper and UI components
        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()

        // Set click listener for items in the RecyclerView
        adapter?.setOnClickItems {
            // Navigate to the StudentSurveyQuestion1Controller when a survey is clicked
            val intent = Intent(this, StudentSurveyQuestion1Controller::class.java)
            intent.putExtra("StudentName", username)
            intent.putExtra("PublishSurvey", it.SurveyId.toString())
            intent.putExtra("PublishSurveyId", it.Id.toString())
            intent.putExtra("StudentId", studentID)
            startActivity(intent)
        }
    }

    /**
     * Initializes the RecyclerView.
     */
    private fun initView() {
        recyclerView = findViewById(R.id.rcyView)
    }

    /**
     * Initializes the RecyclerView with its layout manager and adapter.
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminPublishSurveyAdapterController(getDatabase())
        recyclerView.adapter = adapter
    }

    /**
     * Retrieves an instance of the database helper.
     */
    private fun getDatabase(): DataBaseHelper {
        sqliteHelper = DataBaseHelper(this)
        return sqliteHelper
    }

    /**
     * Retrieves and displays published surveys for the student.
     */
    private fun getPublishedSurveys() {
        val studentID = intent.getStringExtra("StudentId").toString()
        val psList = sqliteHelper.getPublishedSurveyforStudentPage(studentID)
        adapter?.addItems(psList)
    }

    /**
     * Logs out the student.
     */
    fun logout(view: View) {
        val intent = Intent(this, MainLoginController::class.java)
        startActivity(intent)
    }
}
