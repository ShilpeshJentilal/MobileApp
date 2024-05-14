package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class StudentActivity : AppCompatActivity() {

    private lateinit var sqliteHelper:DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: PublishedSurveyAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        getSupportActionBar()?.setTitle("Student HomePage")
        val welcome = findViewById<TextView>(R.id.txtwelcomeStudent)
        val intent: Intent = getIntent()
        val username = intent.getStringExtra("StudentName").toString()


        welcome.setText("Welcome $username").toString().toUpperCase()

        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()
       adapter?.setOnClickItems{

           val intent = Intent(this, SurveyQuestion1_Activity::class.java)
           val getintent = getIntent()
           val studentID = getintent.getStringExtra("StudentId").toString()
           val studentName = getintent.getStringExtra("StudentName").toString()
           intent.putExtra("StudentName",studentName)
           intent.putExtra("PublishSurvey",it.SurveyId.toString())
           intent.putExtra("PublishSurveyId",it.Id.toString())
           intent.putExtra("StudentId",studentID)
           startActivity(intent)
       }
    }

    private fun initView(){
        recyclerView = findViewById(R.id.rcyView)
    }

    private fun initRecyclerView(){
        val db = DataBaseHelper(this)
        val intent: Intent = getIntent()
        val username = intent.getStringExtra("StudentName").toString()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PublishedSurveyAdapter(getDatabase())
        recyclerView.adapter = adapter
    }

    private fun getDatabase():DataBaseHelper{
        sqliteHelper = DataBaseHelper(this)
        return sqliteHelper
    }

    private fun getPublishedSurveys(){
        val studentID = intent.getStringExtra("StudentId").toString()
        val psList = sqliteHelper.getPublishedSurveyforStudentPage(studentID)
        adapter?.addItems(psList)
    }


    fun logout(view:View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}


