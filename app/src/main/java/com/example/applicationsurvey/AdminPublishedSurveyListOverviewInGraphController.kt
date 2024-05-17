package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class AdminPublishedSurveyListOverviewInGraphController : AppCompatActivity() {

    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: AdminPublishedSurveyListAdapterController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_published_survey_listfor_graph)

        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()

        adapter?.setOnClickView { publishedSurvey ->
            val intent = Intent(this, AdminSurveyRespondOverviewGraphController::class.java).apply {
                putExtra("PublishedSurvey", publishedSurvey)
            }
            startActivity(intent)
        }
    }

    /**
     * Initializes the view components.
     */
    private fun initView() {
        recyclerView = findViewById(R.id.itemryclerinsurveylist2)
    }

    /**
     * Initializes the RecyclerView with a layout manager and adapter.
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminPublishedSurveyListAdapterController(sqliteHelper)
        recyclerView.adapter = adapter
    }

    /**
     * Fetches the list of published surveys from the database and updates the adapter.
     */
    private fun getPublishedSurveys() {
        val publishedSurveys = sqliteHelper.getPublishedSurvey()
        adapter?.addItems(publishedSurveys)
    }
}
