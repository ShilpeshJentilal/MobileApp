package com.example.applicationsurvey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Survey

class SurveyAdapter(dataBaseHelper: DataBaseHelper): RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {

    val db = dataBaseHelper
    private var surveyList: ArrayList<Survey> = ArrayList()
    private var onClickItem: ((Survey) ->Unit)? = null

    fun addItems(items: ArrayList<Survey>){
        this.surveyList = items
    }

    fun setOnClickItems(callback: (Survey)->Unit){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int) = SurveyAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.lo_surveylist, parent, false),
            db
        )


    override fun onBindViewHolder(holder: ViewHolder, p: Int) {
        val s = surveyList[p]
        var count = p +1
        holder.bindView(s,count)
        holder.btnDelete.setOnClickListener { onClickItem?.invoke(s) }
    }

    override fun getItemCount(): Int {
    return surveyList.size
    }

class ViewHolder(var view: View, var db:DataBaseHelper):RecyclerView.ViewHolder(view){

    val txtModule = itemView.findViewById<TextView>(R.id.txtSurveyTitleinDeleteActivity)
    val txtnumber = itemView.findViewById<TextView>(R.id.txtnumberfordelete)
    val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)

    fun  bindView(surveys:Survey,count:Int){
        txtModule.text = surveys.Title
        txtnumber.text = count.toString()
    }

    private fun getdb():DataBaseHelper {
        return db
    }
}

}
