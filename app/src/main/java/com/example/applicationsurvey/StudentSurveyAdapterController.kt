package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Survey

class StudentSurveyAdapterController(dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<StudentSurveyAdapterController.ViewHolder>() {

    // Database helper instance
    private val db = dataBaseHelper
    private var surveyList: ArrayList<Survey> = ArrayList()
    private var onClickItem: ((Survey) -> Unit)? = null

    // Function to add survey items to the adapter
    fun addItems(items: ArrayList<Survey>){
        this.surveyList = items
    }

    // Function to set click listener for survey items
    fun setOnClickItems(callback: (Survey) -> Unit){
        this.onClickItem = callback
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.lo_surveylist, parent, false),
            db
        )

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val survey = surveyList[position]
        val count = position + 1
        holder.bindView(survey, count)
        holder.btnDelete.setOnClickListener { onClickItem?.invoke(survey) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return surveyList.size
    }

    // ViewHolder class to hold the views
    class ViewHolder(private val view: View, private val db: DataBaseHelper): RecyclerView.ViewHolder(view) {

        // Views in the item layout
        private val txtModule = itemView.findViewById<TextView>(R.id.txtSurveyTitleinDeleteActivity)
        private val txtnumber = itemView.findViewById<TextView>(R.id.txtnumberfordelete)
        val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)

        // Bind data to the views
        fun bindView(survey: Survey, count: Int) {
            txtModule.text = survey.Title
            txtnumber.text = count.toString()
        }
    }
}
