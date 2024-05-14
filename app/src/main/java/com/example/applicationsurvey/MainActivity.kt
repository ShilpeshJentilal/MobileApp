package com.example.applicationsurvey

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.applicationsurvey.Model.Admin
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Student

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setTitle("Survey")
        deleteExpiredPublishedSurveys()

    }

    /**
     * Takes the user to the register page.
     * @param view The view that triggered the function.
     */
    fun gotoRegiserPage(view: View){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun deleteExpiredPublishedSurveys(){
        val db=DataBaseHelper(this)
       // db.deleteExpiredPublishedSurveys()
    }


    /**
     * @param view The view that triggered the login attempt (the login button).
     *
     * This function attempts to log in the user using the provided username and password. If the login is successful,
     * the user is redirected to either the AdminActivity or the StudentActivity, depending on whether they are an
     * administrator or a student. If the login is unsuccessful, an error message is displayed.
     */
    fun loginButton(view: View){
        val userName = findViewById<EditText>(R.id.txtBoxUsername).text.toString().lowercase()
        val userPassword = findViewById<EditText>(R.id.txtBoxPassword).text.toString()
        val message = findViewById<TextView>(R.id.txtmessage)
        val db = DataBaseHelper(this)

        if(userName.isEmpty()||userPassword.isEmpty()) {
            message.text = "Please insert Username and Password"
        }else {
            val myDataBase = DataBaseHelper(this)
            val result = myDataBase.getUser(Student(-1, userName, userPassword))
            val result2 = myDataBase.getAdminUser(Admin(-1,userName,userPassword))

            if (result == -1) {
                if (result2 == -1){
                    message.text = "User does not exist"
                }else if (result == -2){
                    message.text = "Cannot open database"
                }else{
                    Toast.makeText(this, "You logged in successfully as admin", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("Admin",userName)
                    startActivity(intent)
                }
            } else if (result == -2) {
                message.text = "Error Can't Open Database"
            } else {
                var studentId = db.getStudentID(userName).toString()
                Toast.makeText(this, "You logged in successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, StudentActivity::class.java)
                intent.putExtra("StudentName",userName)
                intent.putExtra("StudentId",studentId.toString())

                startActivity(intent)
            }
        }
    }



}