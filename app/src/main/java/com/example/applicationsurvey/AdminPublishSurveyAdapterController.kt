package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class AdminPublishSurveyAdapterController(private val dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<AdminPublishSurveyAdapterController.ViewHolder>(){

    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var onClickItem: ((PublishedSurvey) -> Unit)? = null

    /**
     * Add items to the survey list.
     * @param items List of PublishedSurvey objects to be added.
     */
    fun addItems(items: ArrayList<PublishedSurvey>) {
        surveyList = items
    }

    /**
     * Set a callback for item click events.
     * @param callback Callback function to handle item click events.
     */
    fun setOnClickItems(callback: (PublishedSurvey) -> Unit) {
        onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lo_publishsurveyss, parent, false)
        return ViewHolder(view, dataBaseHelper)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val survey = surveyList[position]
        val count = position + 1
        holder.bindView(survey, count)
        holder.btnTakesurvey.setOnClickListener { onClickItem?.invoke(survey) }
    }

    override fun getItemCount(): Int = surveyList.size

    class ViewHolder(itemView: View, private val db: DataBaseHelper): RecyclerView.ViewHolder(itemView) {
        private val txtModule = itemView.findViewById<TextView>(R.id.txtModulesurvey)
        private val txtStart = itemView.findViewById<TextView>(R.id.txtmoudlestartdate)
        private val txtEnd = itemView.findViewById<TextView>(R.id.txtmodulenddate)
        var btnTakesurvey = itemView.findViewById<Button>(R.id.btntakeSurvey)
        private val txtCount = itemView.findViewById<TextView>(R.id.txtnumber)

        /**
         * Bind data to views.
         * @param survey PublishedSurvey object containing data.
         * @param count Position of the item in the list.
         */
        fun bindView(survey: PublishedSurvey, count: Int) {
            txtCount.text = "$count)"
            txtModule.text = db.getSurveyTitlebyID(survey.SurveyId)
            txtStart.text = survey.StartDate
            txtEnd.text = survey.EndDate
        }
    }
}
