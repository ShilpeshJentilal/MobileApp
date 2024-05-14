package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class PublishedSurveyListAdapter(dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<PublishedSurveyListAdapter.ViewHolder>() {
    val db = dataBaseHelper
    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var OnClickViewStates:((PublishedSurvey)->Unit)?=null

    fun addItems(items: ArrayList<PublishedSurvey>){
        this.surveyList = items
    }

    fun setOnClickView(callback: (PublishedSurvey)->Unit){
        this.OnClickViewStates = callback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.publishsurveylists, parent, false),db)

    override fun onBindViewHolder(holder: ViewHolder, p: Int) {
        val s = surveyList[p]
        val count = p+1
        holder.bindView(s,count)
        holder.btnViewStates.setOnClickListener{OnClickViewStates?.invoke(s)}
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }


    class ViewHolder(var view: View, var db: DataBaseHelper): RecyclerView.ViewHolder(view){

        val txtModule = itemView.findViewById<TextView>(R.id.txtsurveytitleinlist)
        val txtcount = itemView.findViewById<TextView>(R.id.txtcountsurveylist)
        val btnViewStates = itemView.findViewById<Button>(R.id.btnViewStates)


        fun  bindView(surveys: PublishedSurvey, count:Int){
            txtcount.text = count.toString() + ")"
            txtModule.text = getdb().getSurveyTitlebyID(surveys.SurveyId)

        }


        private fun getdb(): DataBaseHelper {
            return db
        }



    }
}