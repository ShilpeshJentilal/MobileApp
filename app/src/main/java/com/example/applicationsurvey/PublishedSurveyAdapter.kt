package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.PublishedSurvey

class PublishedSurveyAdapter(dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<PublishedSurveyAdapter.ViewHolder>(){

    val db = dataBaseHelper
    private var surveyList: ArrayList<PublishedSurvey> = ArrayList()
    private var onClickItem: ((PublishedSurvey) ->Unit)? = null


    fun addItems(items: ArrayList<PublishedSurvey>){
        this.surveyList = items
    }

    fun setOnClickItems(callback: (PublishedSurvey)->Unit){
        this.onClickItem = callback
    }



    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.lo_publishsurveyss, parent, false),db)

    override fun onBindViewHolder(holder: ViewHolder, p: Int) {
        val s = surveyList[p]
        val count = p+1
        holder.bindView(s,count)
        holder.btnTakesurvey.setOnClickListener{onClickItem?.invoke(s)}
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    class ViewHolder(var view: View,var db:DataBaseHelper):RecyclerView.ViewHolder(view){

        val txtModule = itemView.findViewById<TextView>(R.id.txtModulesurvey)
        val txtstart = itemView.findViewById<TextView>(R.id.txtmoudlestartdate)
        val txtend = itemView.findViewById<TextView>(R.id.txtmodulenddate)
        var btnTakesurvey = itemView.findViewById<Button>(R.id.btntakeSurvey)
        val txtCount = itemView.findViewById<TextView>(R.id.txtnumber)

        fun  bindView(surveys:PublishedSurvey,count:Int){
                txtCount.text = count.toString() + ")"
                txtModule.text = getdb().getSurveyTitlebyID(surveys.SurveyId)
                txtstart.text = surveys.StartDate
                txtend.text = surveys.EndDate
        }


        private fun getdb():DataBaseHelper {
           return db
        }


    }


}