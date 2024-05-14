package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class PublishedSurveyListforGraph_Activity : AppCompatActivity() {

    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: PublishedSurveyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_published_survey_listfor_graph)
        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()
        adapter?.setOnClickView {
            val intent = Intent(this, SurveyRespondGraphActivity::class.java)
            intent.putExtra("PublishedSurvey",it)
            startActivity(intent)
        }

    }
    private fun initView() {
        recyclerView = findViewById(R.id.itemryclerinsurveylist2)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PublishedSurveyListAdapter(getDatabase())
        recyclerView.adapter = adapter

    }

    private fun getDatabase(): DataBaseHelper {
        sqliteHelper = DataBaseHelper(this)
        return sqliteHelper
    }

    private fun getPublishedSurveys() {
        val psList = sqliteHelper.getPublishedSurvey()
        adapter?.addItems(psList)
    }
}