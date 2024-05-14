package com.example.applicationsurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.applicationsurvey.Model.DataBaseHelper
import com.example.applicationsurvey.Model.Student

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.setTitle("Register")

    }

    fun saveNewUserButton(view: View){
        val loginName = findViewById<EditText>(R.id.txtBoxUsernameRegister).text.toString().lowercase()
        val password = findViewById<EditText>(R.id.txtBoxPasswordRegister).text.toString()
        val confirmpass = findViewById<EditText>(R.id.txtBoxConfirmPass).text.toString()
        val message = findViewById<TextView>(R.id.txtMessage)
        val pattern = Regex("[A-Za-z0-9]*")
        if(loginName.isEmpty()||password.isEmpty()||confirmpass.isEmpty()) {
            message.text = "Please detail must be filled in"
        }else if(pattern.matches(loginName)==false){
            message.text = "Username Must not cotains symbols!"
        }else if(password != confirmpass) {
            message.text = "Password does not match"
        }else{
            val newUser = Student(-1,loginName,password)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addStudent(newUser)
            when(result) {
                1 -> {
                    Toast.makeText(this,"Your details has been add to the database successfully", Toast.LENGTH_LONG).show()
                    findViewById<Button>(R.id.btnSign_Up).isEnabled = false
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                }
                -1 -> message.text = "Error on creating new account"
                -2 -> message.text = "Error can not open database"
                -3 -> message.text = "Username is already exist"
            }
        }

    }





}