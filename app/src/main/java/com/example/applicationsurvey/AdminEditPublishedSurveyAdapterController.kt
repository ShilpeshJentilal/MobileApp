package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class AdminEditPublishedSurveyAdapterController(private val dataBaseHelper: DataBaseHelper) : RecyclerView.Adapter<AdminEditPublishedSurveyAdapterController.ViewHolder>() {
    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var onClickEdit: ((PublishedSurvey) -> Unit)? = null
    private var onClickDelete: ((PublishedSurvey) -> Unit)? = null

    /**
     * Adds items to the list of published surveys.
     * @param items The list of published surveys to add.
     */
    fun addItems(items: ArrayList<PublishedSurvey>) {
        this.surveyList = items
    }

    /**
     * Sets a callback for when the delete button is clicked.
     * @param callback The callback function to be executed when the delete button is clicked.
     */
    fun setOnClickDelete(callback: (PublishedSurvey) -> Unit) {
        this.onClickDelete = callback
    }

    /**
     * Sets a callback for when the edit button is clicked.
     * @param callback The callback function to be executed when the edit button is clicked.
     */
    fun setOnClickEdit(callback: (PublishedSurvey) -> Unit) {
        this.onClickEdit = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lo_publishedsurveysforedit, parent, false)
        return ViewHolder(view, dataBaseHelper)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val survey = surveyList[position]
        val count = position + 1
        holder.bindView(survey, count)
        holder.btndelete.setOnClickListener { onClickDelete?.invoke(survey) }
        holder.btnedit.setOnClickListener { onClickEdit?.invoke(survey) }
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    class ViewHolder(view: View, private val db: DataBaseHelper) : RecyclerView.ViewHolder(view) {
        val txtModule: TextView = itemView.findViewById(R.id.txtModulesurveyforps)
        val txtStart: TextView = itemView.findViewById(R.id.txtmodulstartdateforps)
        val txtEnd: TextView = itemView.findViewById(R.id.txtmoudleenddateforps)
        val btnedit: Button = itemView.findViewById(R.id.btnEditps)
        val btndelete: Button = itemView.findViewById(R.id.btnDeletePS)
        val txtCount: TextView = itemView.findViewById(R.id.txtnumberforps)

        /**
         * Binds the published survey data to the TextViews and Buttons of the RecyclerView item.
         * @param survey The PublishedSurvey object to be displayed in the RecyclerView item.
         * @param count The count of the published survey in the list.
         */
        fun bindView(survey: PublishedSurvey, count: Int) {
            txtCount.text = "$count)"
            txtModule.text = db.getSurveyTitlebyID(survey.SurveyId)
            txtStart.text = survey.StartDate
            txtEnd.text = survey.EndDate
        }
    }
}
