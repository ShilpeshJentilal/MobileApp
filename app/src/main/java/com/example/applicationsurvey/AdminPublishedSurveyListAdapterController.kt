package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class AdminPublishedSurveyListAdapterController(private val dataBaseHelper: DataBaseHelper) : RecyclerView.Adapter<AdminPublishedSurveyListAdapterController.ViewHolder>() {

    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var onClickViewStates: ((PublishedSurvey) -> Unit)? = null

    /**
     * Adds items to the list of published surveys.
     * @param items: ArrayList<PublishedSurvey> - the list of published surveys to add.
     */
    fun addItems(items: ArrayList<PublishedSurvey>) {
        this.surveyList = items
        notifyDataSetChanged() // Notify the adapter to refresh the list
    }

    /**
     * Sets a callback for when the view states button is clicked.
     * @param callback: (PublishedSurvey) -> Unit - the callback function to be executed.
     */
    fun setOnClickView(callback: (PublishedSurvey) -> Unit) {
        this.onClickViewStates = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publishsurveylists, parent, false)
        return ViewHolder(view, dataBaseHelper)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val survey = surveyList[position]
        holder.bindView(survey, position + 1)
        holder.btnViewStates.setOnClickListener { onClickViewStates?.invoke(survey) }
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    class ViewHolder(view: View, private val db: DataBaseHelper) : RecyclerView.ViewHolder(view) {

        private val txtModule: TextView = itemView.findViewById(R.id.txtsurveytitleinlist)
        private val txtCount: TextView = itemView.findViewById(R.id.txtcountsurveylist)
        val btnViewStates: Button = itemView.findViewById(R.id.btnViewStates)

        /**
         * Binds the survey data to the views.
         * @param survey: PublishedSurvey - the survey to be displayed.
         * @param count: Int - the count of the survey in the list.
         */
        fun bindView(survey: PublishedSurvey, count: Int) {
            txtCount.text = "$count)"
            txtModule.text = db.getSurveyTitlebyID(survey.SurveyId)
        }
    }
}
