package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper

class DeleteSurveyActivity : AppCompatActivity() {
    private lateinit var sqliteHelper:DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: SurveyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_survey)
        getSupportActionBar()?.setTitle("Delete Survey")
        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()

        /**
         * Set the OnClickListener for each item in the RecyclerView. When an item is clicked, a
         * confirmation dialog will be displayed to confirm the user's intention to delete the survey. If
         * the user confirms, the survey and all related published surveys will be deleted from the
         * database. If the user cancels, the dialog is dismissed and no action is taken.
         *
         * @param onClickItems the OnClickListener to set for each item in the RecyclerView
         */
        adapter?.setOnClickItems{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deleting this survey will remove publish survey related to the survey.\n\nAre you sure you want to delete this Survey?")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes"){ dialog,_->
                sqliteHelper.deleteSurvey(it.Id)
                sqliteHelper.deleteQuestion(it.Id)
                sqliteHelper.deletePublishSurvey(it.Id)
                val intent = Intent(this, AdminActivity::class.java)
                val getintent = getIntent()
                val studentName = getintent.getStringExtra("StudentName").toString()
                intent.putExtra("StudentName",studentName)
                startActivity(intent)
            }
            builder.setNegativeButton("No"){ dialog,_->
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }



    }


    override fun onBackPressed() {
    }

    /**
     * Initializes the view elements.
     */
    private fun initView(){
        recyclerView = findViewById(R.id.rcySurveyview)
    }

    /**

    Initializes the RecyclerView object and sets its layout manager, adapter and data.
     */
    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SurveyAdapter(getDatabase())
        recyclerView.adapter = adapter
    }


    /**
     * This function gets database
     */
    private fun getDatabase(): DataBaseHelper {
        sqliteHelper = DataBaseHelper(this)
        return sqliteHelper
    }


    /**

    This function retrieves a list of published surveys from the database and adds them to the adapter
    for display in the recycler view.
     */
    private fun getPublishedSurveys(){
        val psList = sqliteHelper.getSurveyList()
        adapter?.addItems(psList)
    }

}