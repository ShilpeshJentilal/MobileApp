package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class EditPublishedSurveyAdapter (dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<EditPublishedSurveyAdapter.ViewHolder>(){
    val db = dataBaseHelper
    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var onClickEdit: ((PublishedSurvey) ->Unit)? = null
    private var OnClickDelete:((PublishedSurvey)->Unit)?=null

    /**
     * Adds items to the list of published surveys.
     * @param items: ArrayList<PublishedSurvey>: the list of published surveys to add.
     */
    fun addItems(items: ArrayList<PublishedSurvey>){
        this.surveyList = items
    }


    /**
     * Sets a callback for when the delete button is clicked.
     * @param callback: (PublishedSurvey)->Unit: the callback function to be executed when the delete button is clicked.
     */
    fun setOnClickDelete(callback: (PublishedSurvey)->Unit){
        this.OnClickDelete = callback
    }


    /**
     * Sets a callback for when the edit button is clicked.
     * @param callback: (PublishedSurvey)->Unit: the callback function to be executed when the edit button is clicked.
     */
    fun setOnClickEdit(callback: (PublishedSurvey)->Unit){
        this.onClickEdit = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.lo_publishedsurveysforedit, parent, false),db)

    override fun onBindViewHolder(holder: ViewHolder, p: Int) {
        val s = surveyList[p]

        val count = p+1
        holder.bindView(s,count)
        holder.btndelete.setOnClickListener{OnClickDelete?.invoke(s)}
        holder.btnedit.setOnClickListener { onClickEdit?.invoke(s) }
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }


    class ViewHolder(var view: View, var db: DataBaseHelper): RecyclerView.ViewHolder(view){

        val txtModule = itemView.findViewById<TextView>(R.id.txtModulesurveyforps)
        val txtstart = itemView.findViewById<TextView>(R.id.txtmodulstartdateforps)
        val txtend = itemView.findViewById<TextView>(R.id.txtmoudleenddateforps)
        val btnedit = itemView.findViewById<Button>(R.id.btnEditps)
        val btndelete = itemView.findViewById<Button>(R.id.btnDeletePS)
        val txtCount = itemView.findViewById<TextView>(R.id.txtnumberforps)


        /**
         * This function is used to bind the published survey data to the TextViews and Buttons of the RecyclerView item.
         *
         * @param surveys is the PublishedSurvey object to be displayed in the RecyclerView item.
         * @param count is the count of the published survey in the list.
         */
        fun  bindView(surveys: PublishedSurvey, count:Int){
            txtCount.text = count.toString() + ")"
            txtModule.text = getdb().getSurveyTitlebyID(surveys.SurveyId)
            txtstart.text = surveys.StartDate
            txtend.text = surveys.EndDate
        }

        /**
        This function returns the DataBaseHelper object.

        @return the database.
         */
        private fun getdb(): DataBaseHelper {
            return db
        }



    }
}