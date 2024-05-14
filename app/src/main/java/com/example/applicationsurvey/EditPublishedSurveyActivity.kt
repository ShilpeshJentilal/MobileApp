package com.example.applicationsurvey

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey
import java.util.*

class EditPublishedSurveyActivity : AppCompatActivity() {
    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: EditPublishedSurveyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_published_survey)
        getSupportActionBar()?.setTitle("Edit/ Remove Published Survey")

        sqliteHelper = DataBaseHelper(this)
        initView()
        initRecyclerView()
        getPublishedSurveys()
        adapter?.setOnClickDelete { delete(it) }
        adapter?.setOnClickEdit { edit(it) }
    }


    private fun initView() {
        recyclerView = findViewById(R.id.rcyEditPs)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EditPublishedSurveyAdapter(getDatabase())
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

    override fun onBackPressed() {
    }


    /**
     * Deletes a Published Survey object from the database.
     * @param it The Published Survey object to be deleted.
     */
    fun delete(it: PublishedSurvey) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Deleting this Published Survey will be removed from the data.\n\nAre you sure you want to delete this Published Survey?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper.deletePublishSurvey(it.SurveyId)
            Toast.makeText(this, "Sucessfully deleted", Toast.LENGTH_LONG).show()
            intent = Intent(this, EditPublishedSurveyActivity::class.java)
            val getintent = getIntent()
            val studentName = getintent.getStringExtra("StudentName").toString()
            intent.putExtra("StudentName",studentName)
            startActivity(intent)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


//https://www.youtube.com/watch?v=2Nj6qCtaUqw&ab_channel=AtifPervaiz


    /**
     * This function allows an admin to edit a published survey by updating the end date.
     * @param it: PublishedSurvey - the published survey to be edited
     *
     * The function displays an alert dialog containing an editable text field for the end date and a calendar button to
     * select the end date. The admin can update the end date by clicking the update button or cancel the operation by
     * clicking the cancel button. If the start date is later than or the same as the end date, a toast message will be
     * displayed indicating that the end date cannot be before or the same as the start date. If the end date is successfully
     * updated, the function will close the alert dialog and display a toast message indicating the update was successful.
     *
     * The function will then navigate back to the Edit Published Survey Activity page.
     */
    private fun edit(it: PublishedSurvey) {
        val surveyTitle = sqliteHelper.getSurveyTitlebyID(it.SurveyId)
        val dialog = LayoutInflater.from(this).inflate(R.layout.lo_editpublishsurvey,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialog)
        builder.setTitle("Update $surveyTitle")
        val alertDialog = builder.show()
        val btnCalendar = dialog.findViewById<Button>(R.id.btnEditDate)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnUpdate = dialog.findViewById<Button>(R.id.btnUpdate)
        val moduleTitle = dialog.findViewById<EditText>(R.id.txtboxeditmodule)
        moduleTitle.setText(sqliteHelper.getSurveyTitlebyID(it.SurveyId))
        val startdate = dialog.findViewById<EditText>(R.id.txtBoxstart)
        startdate.setText(it.StartDate)
        val enddate = dialog.findViewById<EditText>(R.id.txtBoxend)
        enddate.setText(it.EndDate)

        val ps = PublishedSurvey(it.Id,it.SurveyId,it.StartDate,it.EndDate)

        btnCalendar.setOnClickListener{
            val EndDatePicker = object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var endDay = 0
                    var endMonth = 0
                    var endYear = 0
                    endDay = dayOfMonth
                    endMonth = month + 1
                    endYear = year
                    if(endDay.toString().length==1 && endMonth.toString().length ==1) {
                        enddate.setText("$endYear-0$endMonth-0$endDay")
                    }else if(endDay.toString().length==1&& endMonth.toString().length >1) {
                        enddate.setText("$endYear-$endMonth-0$endDay")
                    }else if(endDay.toString().length>1&& endMonth.toString().length ==1){
                        enddate.setText("$endYear-0$endMonth-$endDay")
                    }else{
                        enddate.setText("$endYear-$endMonth-$endDay")
                    }
                }
            }
            getDateTimeCalender()
            DatePickerDialog(this,EndDatePicker, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
        }

        btnUpdate.setOnClickListener {
            val startDay = startdate.text.toString().substring(8, 10).toInt()
            val startMonth = startdate.text.toString().substring(5, 7).toInt()
            val startYear = startdate.text.toString().substring(0, 4).toInt()
            var endDay = enddate.text.toString().substring(8, 10).toInt()
            var endMonth = enddate.text.toString().substring(5, 7).toInt()
            var endYear = enddate.text.toString().substring(0,4).toInt()
            var enddate1 = dialog.findViewById<EditText>(R.id.txtBoxend).text.toString()
            if (startDay >= endDay && startMonth >= endMonth && startYear >= endYear) {
                Toast.makeText(this, "End Date cannot be less than Today's Date or Same", Toast.LENGTH_SHORT).show()
            }else{
                val updatedps = PublishedSurvey(ps.Id,ps.SurveyId,ps.StartDate,enddate1)
                sqliteHelper.updatePublishedSurvey(updatedps)
                alertDialog.dismiss()
                Toast.makeText(this, "Sucessfully Updated", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,EditPublishedSurveyActivity::class.java)
                startActivity(intent)
            }
        }

    btnCancel.setOnClickListener {
        alertDialog.dismiss()
    }

}

    /**
     * Get the current date and time from the device's Calendar instance.
     */
    fun getDateTimeCalender() {
        val cal = Calendar.getInstance()
        var DefaultDay = 0
        var DefaultMonth = 0
        var DefaultYear = 0
        DefaultDay = cal.get(Calendar.DAY_OF_MONTH)
        DefaultMonth = cal.get(Calendar.MONTH)
        DefaultYear = cal.get(Calendar.YEAR)
    }


}
