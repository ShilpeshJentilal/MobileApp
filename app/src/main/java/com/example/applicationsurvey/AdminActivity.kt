package com.example.applicationsurvey
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

/**
 * An activity that displays the admin portal.
 */
class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the activity
        setContentView(R.layout.activity_admin)
        // Set the title of the action bar
        getSupportActionBar()?.setTitle("Admin Portal")
        // Get the welcome message TextView and the intent
        val welcome = findViewById<TextView>(R.id.txtWelcome)
        val intent:Intent =getIntent()
        // Get the "Admin" string extra from the intent
        val welcomeUser = intent.getStringExtra("Admin")
        val Username = welcomeUser
        // Set the welcome message
        welcome.setText("Welcome $Username")

    }

    override fun onBackPressed() {
    }

    /**
     * Navigates to the CreateSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoCreateSurevy(view: View){
        // Create an intent to start the CreateSurveyActivity
        val intent = Intent(this,CreateSurveyActivity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the CreateSurveyActivity
        startActivity(intent)
    }

    /**
     * Navigates to the PublishedSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoPublishedSurvey(view: View){
        // Create an intent to start the PublishedSurveyActivity
        val intent = Intent(this,PublishedSurveyActivity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the PublishedSurveyActivity
        startActivity(intent)
    }

    /**
     * Navigates to the DeleteSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDeleteSurvey(view: View){
        // Create an intent to start the DeleteSurveyActivity
        val intent = Intent(this,DeleteSurveyActivity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the DeleteSurveyActivity
        startActivity(intent)
    }


    /**
     * Navigates to the EditPublishedSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoEdiPublishedSurvey(view: View){
        // Create an intent to start the EditPublishedSurveyActivity
        val intent = Intent(this,EditPublishedSurveyActivity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the EditPublishedSurveyActivity
        startActivity(intent)
    }



    /**
     * Navigates to the PublishedSurveyListActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDisplaySurveyStates(view: View){
        // Create an intent to start the PublishedSurveyListActivity
        val intent = Intent(this,PublishedSurveyListActivity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the PublishedSurveyListActivity
        startActivity(intent)
    }



    /**
     * Navigates to the PublishedSurveyListActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDisplaySurveyInGraph(view: View){
        // Create an intent to start the PublishedSurveyListActivity
        val intent = Intent(this,PublishedSurveyListforGraph_Activity::class.java)
        // Get the "Admin" string extra from the current intent
        val User = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin",User)
        // Start the PublishedSurveyListActivity
        startActivity(intent)
    }

    /**
     * Logs out the current user and navigates to the MainActivity.
     *
     * @param view The view that was clicked.
     */
    fun Logout(view: View){
        // Create an intent to start the MainActivity
        val intent = Intent(this,MainActivity::class.java)
        // Start the MainActivity
        startActivity(intent)
    }


}