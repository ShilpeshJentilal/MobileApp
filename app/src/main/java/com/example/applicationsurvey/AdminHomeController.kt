package com.example.applicationsurvey

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * An activity that displays the admin portal.
 */
class AdminHomeController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the activity
        setContentView(R.layout.activity_admin)
        // Set the title of the action bar
        supportActionBar?.title = "Admin Portal"

        // Get the welcome message TextView and the intent
        val welcome = findViewById<TextView>(R.id.txtWelcome)
        val welcomeUser = intent.getStringExtra("Admin")
        // Set the welcome message
        welcome.text = "Welcome $welcomeUser"
    }

    override fun onBackPressed() {
        // Do nothing on back pressed
    }

    /**
     * Navigates to the specified activity.
     *
     * @param view The view that was clicked.
     * @param targetActivity The class of the target activity.
     */
    private fun navigateTo(view: View, targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        // Get the "Admin" string extra from the current intent
        val user = intent.getStringExtra("Admin")
        // Add the "Admin" string extra to the new intent
        intent.putExtra("Admin", user)
        // Start the target activity
        startActivity(intent)
    }

    /**
     * Navigates to the CreateSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoCreateSurvey(view: View) {
        navigateTo(view, AdminCreateSurveyController::class.java)
    }

    /**
     * Navigates to the PublishedSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoPublishedSurvey(view: View) {
        navigateTo(view, AdminPublishSurveyController::class.java)
    }

    /**
     * Navigates to the DeleteSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDeleteSurvey(view: View) {
        navigateTo(view, AdminDeleteSurveyController::class.java)
    }

    /**
     * Navigates to the EditPublishedSurveyActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoEditPublishedSurvey(view: View) {
        navigateTo(view, AdminEditPublishedSurveyController::class.java)
    }

    /**
     * Navigates to the PublishedSurveyListActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDisplaySurveyStates(view: View) {
        navigateTo(view, AdminPublishedSurveyListController::class.java)
    }

    /**
     * Navigates to the PublishedSurveyListOverviewInGraphActivity.
     *
     * @param view The view that was clicked.
     */
    fun gotoDisplaySurveyInGraph(view: View) {
        navigateTo(view, AdminPublishedSurveyListOverviewInGraphController::class.java)
    }

    /**
     * Logs out the current user and navigates to the MainActivity.
     *
     * @param view The view that was clicked.
     */
    fun logout(view: View) {
        startActivity(Intent(this, MainLoginController::class.java))
    }
}
