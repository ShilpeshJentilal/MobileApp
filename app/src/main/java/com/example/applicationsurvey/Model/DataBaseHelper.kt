package com.example.applicationsurvey.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


private val DataBaseName = "AppDataBase.db"
private val ver : Int = 1

class DataBaseHelper(context: Context) :SQLiteOpenHelper(context, DataBaseName,null,ver){
    /* Admin Table */
    private val AdminTableName = "Admin"
    private val AdminColumn_ID = "Id"
    private val Column_AdminLoginName = "LoginName"
    private val Column_AdminPassword = "PassWord"

    /* Student Table */
    private val StudentTableName = "Student"
    private val StudentColumn_ID = "Id"
    private val Column_StudentLoginName = "LoginName"
    private val Column_StudentPassword = "PassWord"

    /* Survey Table */
    private val SurveyTable = "Survey"
    private val SurveyColumn_ID = "Id"
    private val Column_SurveyTitle = "Title"

    /* Question Table */
    private val QuestionTable = "Question"
    private val QuestionColumn_ID = "Id"
    private val Column_SurveyID = "SurveyId"
    private val Column_QuestionText = "QuestionText"

    /* Published Table*/
    private val PublishedSurveyTable = "PublishedSurvey"
    private val PublishedSurveyColumn_ID = "Id"
    private val Column_PublishedSurvey_SurveyID = "SurveyId"
    private val Column_PublishedSurvey_StartDate = "StartDate"
    private val Column_PublishedSurvey_EndDate = "EndDate"

    /* Answer Table*/
    private val AnswerTable = "Answer"
    private val AnswerColumn_ID = "Id"
    private val Column_Answer_AnswerText = "AnswerText"

    /* Student Survey Respond Table*/
    private val StudentSurveyRespondTable = "StudentSurveyRespond"
    private val StudentSurveyRespondTableColumn_ID = "Id"
    private val Column_StudentSurveyRespondTable_StudentID= "StudentId"
    private val Column_StudentSurveyRespondTable_PublishSurveyID= "PublishSurveyId"
    private val Column_StudentSurveyRespondTable_QuestionID= "QuestionId"
    private val Column_StudentSurveyRespondTable_AnswerID= "AnswerId"





    override fun onCreate(db: SQLiteDatabase?) {
        try {
            //admin table
            var sqlCreateStatement: String = "CREATE TABLE "+AdminTableName+" ("+AdminColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_AdminLoginName + " TEXT NOT NULL, "+ Column_AdminPassword +" TEXT NOT NULL)"
            db?.execSQL(sqlCreateStatement)


            //student table
            sqlCreateStatement ="CREATE TABLE "+StudentTableName+" ("+StudentColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_StudentLoginName + " TEXT NOT NULL, "+ Column_StudentPassword +" TEXT NOT NULL)"
            db?.execSQL(sqlCreateStatement)

            //Survey table
            sqlCreateStatement ="CREATE TABLE "+SurveyTable+" ("+SurveyColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_SurveyTitle + " TEXT NOT NULL)"
            db?.execSQL(sqlCreateStatement)

            //Question table
            sqlCreateStatement ="CREATE TABLE "+QuestionTable+" ("+QuestionColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_SurveyID + " INTEGER NOT NULL, "+ Column_QuestionText +" TEXT NOT NULL)"
            db?.execSQL(sqlCreateStatement)

            //Published Survey table
            sqlCreateStatement ="CREATE TABLE "+PublishedSurveyTable+" ("+PublishedSurveyColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_PublishedSurvey_SurveyID + " INTEGER NOT NULL, "+ Column_PublishedSurvey_StartDate +" TEXT NOT NULL, "+Column_PublishedSurvey_EndDate+" TEXT NOT NULL)"
            db?.execSQL(sqlCreateStatement)

            //Answer Survey table
            sqlCreateStatement ="CREATE TABLE "+AnswerTable+" ("+AnswerColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_Answer_AnswerText + " Text NOT NULL)"
            db?.execSQL(sqlCreateStatement)

            //Student Survey Respond table
            sqlCreateStatement ="CREATE TABLE "+StudentSurveyRespondTable+" ("+StudentSurveyRespondTableColumn_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Column_StudentSurveyRespondTable_StudentID +
                    " INTEGER NOT NULL, "+Column_StudentSurveyRespondTable_PublishSurveyID + " INTEGER NOT NULL, "+
                    Column_StudentSurveyRespondTable_QuestionID + " INTEGER NOT NULL, "+Column_StudentSurveyRespondTable_AnswerID + " INTEGER NOT NULL)"
            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    /**
     * This function adds a survey to the database.
     * @param survey: a Survey object which will be added to the database
     * @return an integer value which represents the success of the operation:
     * -1: error in creating the survey
     * 1: success in creating the survey
     */
    fun addSurvey(survey: Survey):Int{
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put(Column_SurveyTitle, survey.Title)
        val success = db.insert(SurveyTable,null,cv)
        db.close()
        if(success.toInt()== -1)return success.toInt()//Error, creating a survey
        else return 1
    }






    /**
     * Adds a new question to the database.
     * @param question The {@link Question} object to add to the database.
     * @param survey The {@link Survey} object to which the question belongs.
     * @return An integer indicating the result of the operation. Returns 1 if the question was successfully added,
     * Copy code, -1 if an error occurred while adding the question.
     */
    fun addQuestion(question: Question,survey: Survey):Int{
        val surveyTitle:String = survey.Title
        // writableDatabase for insert actions
        val db:SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        val sqlStatement = "SELECT * FROM $SurveyTable WHERE $Column_SurveyTitle = ? "
        val param = arrayOf(surveyTitle)
        val cursor: Cursor = db.rawQuery(sqlStatement,param)
        var surveyID:Int=0
        if(cursor.moveToFirst()){
            surveyID = cursor.getInt(0)
        }else{
            surveyID = 1
        }
        cv.put(Column_SurveyID,surveyID)
        cv.put(Column_QuestionText, question.QuestionText)
        val success = db.insert(QuestionTable,null,cv)
        cursor.close()
        db.close()
        if(success.toInt()== -1)return success.toInt()//Error, creating a survey
        else return 1
    }



    /**
     * Add a new student survey response to the database.
     *
     * @param it The student survey response to add.
     * @return 1 if the student survey response was added successfully, -1 if an error occurred.
     */
    fun addStudentSurveyRespond(it: StudentSurveyRespond):Int{
        val sucess = getStudentSurveyRespondID(it)
        println(sucess)
        if(sucess>=1){
            val db: SQLiteDatabase = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(StudentSurveyRespondTableColumn_ID,sucess)
            val sucess1 = db.delete(StudentSurveyRespondTable,StudentSurveyRespondTableColumn_ID+"="+sucess,null)
            db.close()
        }
        val db:SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put(Column_StudentSurveyRespondTable_StudentID, it.StudentId)
        cv.put(Column_StudentSurveyRespondTable_PublishSurveyID, it.PublishSurveyId)
        cv.put(Column_StudentSurveyRespondTable_QuestionID, it.QuestionId)
        cv.put(Column_StudentSurveyRespondTable_AnswerID, it.AnswerId)
        val success = db.insert(StudentSurveyRespondTable, null, cv)
        db.close()
        if (success.toInt() == -1) return success.toInt()//Error, adding new user
        else return 1
    }


    /**
     * Adds a new student to the database.
     *
     * @param student the student to be added to the database
     * @return 1 if the student was successfully added, -1 if an error occurred while adding the student
     */

    fun addStudent(student: Student):Int{
        val isStudentLoginNameAlreadyExists = checkStudentLoginName(student)
        if (isStudentLoginNameAlreadyExists<0)
            return isStudentLoginNameAlreadyExists
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_StudentLoginName, student.LoginName)
        cv.put(Column_StudentPassword,student.Password)

        val success = db.insert(StudentTableName,null,cv)
        db.close()
        if(success.toInt()== -1 )return success.toInt()//Error, adding new user
        else return 1

    }


    /**
     * Adds a new published survey to the database.
     *
     * @param publishedSurvey The published survey object to be added to the database.
     * @param surveyTitle The title of the survey associated with the published survey.
     * @return An integer indicating the success of the operation. Returns 1 if the operation was successful, and -1 if it failed.
     */

    fun addPublisheSurvey(publishedSurvey: PublishedSurvey,surveyTitle:String):Int{
        // writableDatabase for insert actions
        val db:SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        val sqlStatement = "SELECT * FROM $SurveyTable WHERE $Column_SurveyTitle = ? "
        val param = arrayOf(surveyTitle)
        val cursor: Cursor = db.rawQuery(sqlStatement,param)
        var surveyID:Int=0
        if(cursor.moveToFirst()){
            surveyID = cursor.getInt(0)
        }
        cv.put(Column_PublishedSurvey_SurveyID,surveyID)
        cv.put(Column_PublishedSurvey_StartDate, publishedSurvey.StartDate)
        cv.put(Column_PublishedSurvey_EndDate,publishedSurvey.EndDate)
        val success = db.insert(PublishedSurveyTable,null,cv)
        cursor.close()
        db.close()
        if(success.toInt()== -1)return success.toInt()//Error, creating a survey
        else return 1
    }


    /**
     * Retrieves all published surveys from the database and returns them as a list.
     *
     * @return A list of published surveys.
     */
    fun getPublishedSurvey(): ArrayList<PublishedSurvey> {
        val publishedSurveyList = ArrayList<PublishedSurvey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $PublishedSurveyTable"
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToNext()){
            do{
                val id:Int = cursor.getInt(0)
                val surveyId: Int = cursor.getInt(1)
                val startDate: String = cursor.getString(2)
                val endDate: String = cursor.getString(3)
                val p = PublishedSurvey(id,surveyId,startDate,endDate)
                publishedSurveyList.add(p)
            } while (cursor.moveToNext())
            cursor.close()
            db.close()
        }
        return publishedSurveyList
    }


    /**
     * Retrieves a list of published surveys that are currently available for a student to take.
     * A published survey is considered available if its end date has not passed.
     *
     * @param studentID the ID of the student
     * @return a list of available published surveys
     */
    fun getPublishedSurveyforStudentPage(studentID: String): ArrayList<PublishedSurvey> {
            val publishedSurveyList = ArrayList<PublishedSurvey>()
            val db: SQLiteDatabase = this.readableDatabase
            val sqlStatement = "SELECT * FROM $PublishedSurveyTable WHERE $Column_PublishedSurvey_EndDate>Date()"
            val cursor: Cursor = db.rawQuery(sqlStatement, null)
            if (cursor.moveToNext()) {
                do {
                    val id:Int = cursor.getInt(0)
                    println()
                    val alreadytaken = (checkStudentRespond(studentID.toInt(),id))
                    if(alreadytaken>0){

                    }else {
                        val id: Int = cursor.getInt(0)
                        val surveyId: Int = cursor.getInt(1)
                        val startDate: String = cursor.getString(2)
                        val endDate: String = cursor.getString(3)
                        val p = PublishedSurvey(id, surveyId, startDate, endDate)
                        publishedSurveyList.add(p)
                    }
                } while (cursor.moveToNext())
                cursor.close()
                db.close()
            }
            return publishedSurveyList
    }


    /**
     * Check if a student has already responded to a survey
     *
     * @param surveyId the id of the survey the student is being checked for
     * @param publishedSurveyId the id of the published survey being checked
     * @return the number of times the student has responded to the survey. Returns 0 if the student has not responded.
     */
    fun checkStudentRespond(surveyId: Int,publishedSurveyId:Int): Int {
        val db: SQLiteDatabase = this.readableDatabase
        val surveyId = surveyId
        val psId = publishedSurveyId
        val sqlStatement = "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_StudentID = ? AND $Column_StudentSurveyRespondTable_PublishSurveyID = ? "
        val param = arrayOf(surveyId.toString(),psId.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement, param)
        val count = cursor.count
        cursor.close()
        db.close()
        return count
    }






    /**
     * Retrieves the survey title for a given survey ID.
     * @param Id the ID of the survey to retrieve the title for
     * @return the title of the survey, or an empty string if no survey with the given ID exists
     */
    fun getSurveyTitlebyID(Id: Int):String{
        val db: SQLiteDatabase = this.readableDatabase
        val publishedSurvey = Id
        val sqlStatement = "SELECT * FROM $SurveyTable WHERE $SurveyColumn_ID = ?"
        var title:String =""
        val param = arrayOf(publishedSurvey.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            title = cursor.getString(1)
        }
        return title
    }


    /**
     * Check if a student with the given login name already exists in the database.
     *
     * @param student the student to check
     * @return 0 if the student does not exist, -3 if the student login name already exists,
     *         -2 if there was an error accessing the database
     */
    private fun checkStudentLoginName(student:Student):Int{
        val db: SQLiteDatabase
        try{
            db = this.readableDatabase
        }catch (e: SQLiteException){
            return -2
        }
        val studentLoginName = student.LoginName.lowercase()
        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLoginName = ?"
        val param = arrayOf(studentLoginName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            //Student is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // error if the student login name already exists
        }
        cursor.close()
        db.close()
        return 0//Student not found
    }



    /**
     * Retrieves a user from the database based on the provided login name and password.
     *
     * @param user  The user object containing the login name and password of the user to be retrieved.
     * @return      The ID of the user if the user was found, -1 if the user was not found, and -2 if an error occurred.
     */
    fun getUser(user: Student) : Int {
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }
        val userName = user.LoginName.lowercase()
        val userPassword = user.Password

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLoginName = ? AND $Column_StudentPassword = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }
        cursor.close()
        db.close()
        return -1 //User not found
    }



    /**
     * This function checks if the provided login credentials of an administrator are valid or not.
     *
     * @param user an object of the `Admin` class representing the administrator whose login credentials are to be checked
     * @return an integer value representing the result of the operation. If the login credentials are valid, the function returns the ID of the administrator.
     *         If the login credentials are not valid, the function returns -1. If there is any error while accessing the database, the function returns -2.
     */
    fun getAdminUser(user:Admin):Int{
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }
        val userName = user.LoginName.lowercase()
        val userPassword = user.Password

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Column_AdminLoginName = ? AND $Column_AdminPassword = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }
        cursor.close()
        db.close()
        return -1 //User not found
    }


    /**
     * Retrieves a list of all surveys in the database.
     *
     * @return An ArrayList of Strings containing the titles of all surveys in the database.
     */
    fun getAllSurvey():ArrayList<String> {
        val surveyList = ArrayList<String>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTable"
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst())
            do {
                val title: String = cursor.getString(1)
                val sur = title
                surveyList.add(sur)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return surveyList
    }


    /**
     * Retrieves a list of all surveys from the database.
     *
     * @return An ArrayList of Survey objects representing all surveys in the database.
     */
    fun getSurveyList():ArrayList<Survey> {
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTable"
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)
                val sur = Survey(id,title)
                surveyList.add(sur)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return surveyList
    }



    /**
     * Retrieves a list of questions for a given survey ID.
     *
     * @param surveyID the ID of the survey to retrieve questions for
     * @return a list of {@link Question} objects for the given survey ID
     */

    fun getQuestions(surveyID: String): ArrayList<Question> {
        var questionList= ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $QuestionTable WHERE $Column_SurveyID = ?"
        val param = arrayOf(surveyID)
        val cursor: Cursor = db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst())
            do{
                var id = cursor.getInt(0)
                var surveyid = cursor.getInt(1)
                var questiontext = cursor.getString(2)
                var question = Question(id,surveyid,questiontext)
                questionList.add(question)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return questionList
    }


    /**
     * Returns the ID of the student with the given login name.
     *
     * @param studentname the login name of the student
     * @return the ID of the student, or -1 if the student is not found
     */
    fun getStudentID(studentname:String):Int{
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLoginName = ?"
        val param = arrayOf(studentname)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            var n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }
        cursor.close()
        db.close()
        return -1
    }


/**
 * Returns the ID of the question in the specified survey with the specified text.
 *
 * @param surveyid the ID of the survey in which to search for the question
 * @param questionText the text of the question to search for
 * @return the ID of the question, or -1 if the question was not found
*/
    fun getQuestionID(surveyid:String,questionText:String):Int{
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $QuestionTable WHERE $Column_SurveyID = ? AND $Column_QuestionText = ?"
        val param = arrayOf(surveyid,questionText)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }
        cursor.close()
        db.close()
        return -1 //User not found
    }




    /**
     * Deletes the survey with the specified ID from the database.
     *
     * @param id The ID of the survey to delete.
     * @return The number of rows affected by the delete operation.
     */
    fun deleteSurvey(id: Int):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SurveyColumn_ID,id)
        val sucess = db.delete(SurveyTable,SurveyColumn_ID+"="+id,null)
        db.close()
        return sucess

    }


    /**
     * Deletes a record of a student's survey response from the database.
     * @param studentSurveyRespond An object representing the student's survey response to delete.
     * @return An integer representing the number of rows affected by the delete operation.
     *         Returns -1 if an error occurred.
     */

    fun deleteStudentSurveyRespond(studentSurveyRespond: StudentSurveyRespond):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(StudentSurveyRespondTableColumn_ID,studentSurveyRespond.Id)
        val sucess = db.delete(StudentSurveyRespondTable,StudentSurveyRespondTableColumn_ID+"="+studentSurveyRespond.Id,null)
        db.close()
        return sucess

    }


    /**
     * Deletes a question from the database.
     *
     * @param id the ID of the question to delete
     * @return the number of rows affected, or -1 if an error occurred
     */
    fun deleteQuestion(id: Int):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Column_SurveyID,id)
        val sucess = db.delete(QuestionTable,Column_SurveyID+"="+id,null)
        db.close()
        return sucess
    }


    /**
     * Deletes a published survey from the database.
     *
     * @param id The ID of the published survey to delete.
     * @return The number of rows affected by the deletion.
     */

    fun deletePublishSurvey(id: Int):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Column_PublishedSurvey_SurveyID,id)
        val sucess = db.delete(PublishedSurveyTable,Column_PublishedSurvey_SurveyID+"="+id,null)
        db.close()
        return sucess
    }


    /**
     * Returns the ID of the survey with the given title.
     * @param surveyTitle the title of the survey
     * @return the ID of the survey with the given title, or -1 if no such survey exists
     */
    fun getSurveyIDbyTitle(surveyTitle: String):Int {
        val db: SQLiteDatabase = this.readableDatabase
        val surveyTitle = surveyTitle
        val sqlStatement = "SELECT * FROM $SurveyTable WHERE $Column_SurveyTitle = ? "
        val param = arrayOf(surveyTitle)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)
        if(cursor.moveToFirst()) {
            var surveyID = cursor.getInt(0)
            cursor.close()
            db.close()
            return surveyID
        }
        cursor.close()
        db.close()
        return -1
    }



    /**
     * Checks if a survey has been published.
     *
     * @param surveyId the ID of the survey to check for
     * @return the ID of the published survey if found, or -1 if not found
     */
    fun checkPublishedSurvey(surveyId: Int): Int {
        val db: SQLiteDatabase = this.readableDatabase
        val surveyId = surveyId
        val sqlStatement = "SELECT * FROM $PublishedSurveyTable WHERE $Column_PublishedSurvey_SurveyID = ? "
        val param = arrayOf(surveyId.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement, param)
        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }
        cursor.close()
        db.close()
        return -1
    }




    /**
     * Updates the published survey with the given published survey object.
     * @param ps the published survey object with updated information.
     */
    fun updatePublishedSurvey(ps: PublishedSurvey){
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Column_PublishedSurvey_SurveyID,ps.SurveyId)
        contentValues.put(Column_PublishedSurvey_StartDate,ps.StartDate)
        contentValues.put(Column_PublishedSurvey_EndDate,ps.EndDate)
        val sucess = db.update(PublishedSurveyTable,contentValues, "$PublishedSurveyColumn_ID=${ps.Id}",null)
        db.close()
    }



    /**
     * Returns the ID of a student survey respond record in the database. If the record does not exist, returns -1.
     *
     * @param studentSurveyRespond the student survey respond object
     * @return the ID of the student survey respond record, or -1 if the record does not exist
     */
    fun getStudentSurveyRespondID(studentSurveyRespond: StudentSurveyRespond): Int {
        val db: SQLiteDatabase = this.readableDatabase
        val psId = studentSurveyRespond.PublishSurveyId.toString()
        val stId = studentSurveyRespond.StudentId.toString()
        val aId = studentSurveyRespond.AnswerId.toString()
        val qId = studentSurveyRespond.QuestionId.toString()
        val sqlStatement = "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_StudentID= $stId" +
                " AND $Column_StudentSurveyRespondTable_PublishSurveyID =$psId AND $Column_StudentSurveyRespondTable_QuestionID =$qId"
      //  val param = arrayOf(stId,qId,)
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }else {
            cursor.close()
            db.close()
            return -1
        }
    }



    /**
     * This function checks if a student has responded to a survey.
     *
     * @param studentID: The ID of the student whose responses are being checked.
     * @param publishsurveyID: The ID of the published survey.
     * @return Returns the ID of the student's survey respond if it exists, -1 otherwise.
     */
    fun checkStudentSurveyRespond(studentID: Int,publishsurveyID: Int): Int {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_StudentID= $studentID AND $Column_StudentSurveyRespondTable_PublishSurveyID= $publishsurveyID"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }else {
            cursor.close()
            db.close()
            return -1
        }
    }


    /**
     * This function retrieves the ID of a published survey from the database, given the survey's ID.
     * @param surveyID The ID of the survey whose published ID is being retrieved.
     * @return The ID of the published survey, or -1 if the published survey could not be found.
     */
    fun getPublishedSurveyIDbysurveyID(surveyID:Int):Int{
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $PublishedSurveyTable WHERE $Column_PublishedSurvey_SurveyID= $surveyID"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }else {
            cursor.close()
            db.close()
            return -1
        }
    }


    /**
     * Gets the total number of participants for a published survey.
     * @param it The published survey to get the participation count for.
     * @return The total number of participants for the published survey.
     */
    fun getTotalParticipation(it: PublishedSurvey): Int {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT(DISTINCT $Column_StudentSurveyRespondTable_StudentID) FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id}"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }


    /**
     * Gets the percentage of strongly agree responses for a specific question in a published survey.
     * @param it The published survey to get the percentage of strongly agree responses for.
     * @param questionID The ID of the question to get the percentage of strongly agree responses for.
     * @return The percentage of strongly agree responses for the specified question in the published survey.
     */
    fun getStronglyAgreePercentage(it: PublishedSurvey,questionID:Int):Float {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id} AND $Column_StudentSurveyRespondTable_QuestionID=$questionID AND $Column_StudentSurveyRespondTable_AnswerID=1"
                val cursor: Cursor = db.rawQuery(sqlStatement, null)
                cursor.moveToFirst()
                val count = cursor.count
                cursor.close()
                return count.toFloat()
            }


    /**
     * Gets the percentage of agree responses for a specific question in a published survey.
     * @param it The published survey to get the percentage of agree responses for.
     * @param questionID The ID of the question to get the percentage of agree responses for.
     * @return The percentage of agree responses for the specified question in the published survey.
     */
    fun getAgreePercentage(it: PublishedSurvey,questionID:Int):Float {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id} AND $Column_StudentSurveyRespondTable_QuestionID=$questionID AND $Column_StudentSurveyRespondTable_AnswerID=2"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        cursor.moveToFirst()
        val count = cursor.count
        cursor.close()
        return count.toFloat()
    }




    /**
     * Gets the percentage of neither agree nor disagree responses for a specific question in a published survey.
     * @param it The published survey to get the percentage of neither agree nor disagree responses for.
     * @param questionID The ID of the question to get the percentage of neither agree nor disagree responses for.
     * @return The percentage of neither agree nor disagree responses for the specified question in the published survey.
     */
    fun getNeitherAgreeorDisagreePercentage(it: PublishedSurvey,questionID:Int):Float {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id} AND $Column_StudentSurveyRespondTable_QuestionID=$questionID AND $Column_StudentSurveyRespondTable_AnswerID=3"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        cursor.moveToFirst()
        val count = cursor.count
        cursor.close()
        return count.toFloat()
    }


    /**
     * Gets the percentage of Disagree responses for a specific question in a published survey.
     * @param it The published survey to get the percentage of disagree responses for.
     * @param questionID The ID of the question to get the percentage of disagree responses for.
     * @return The percentage of disagree responses for the specified question in the published survey.
     */
    fun getDisagreePercentage(it: PublishedSurvey,questionID:Int):Float {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id} AND $Column_StudentSurveyRespondTable_QuestionID=$questionID AND $Column_StudentSurveyRespondTable_AnswerID=4"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

            val count = cursor.count
            cursor.close()
            return count.toFloat()

    }

    /**
     * Gets the percentage of strongly Disagree responses for a specific question in a published survey.
     * @param it The published survey to get the percentage of strongly disagree responses for.
     * @param questionID The ID of the question to get the percentage of strongly disagree responses for.
     * @return The percentage of strongly disagree responses for the specified question in the published survey.
     */
    fun getStronglyDisagreePercentage(it: PublishedSurvey,questionID:Int):Float {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement =
            "SELECT * FROM $StudentSurveyRespondTable WHERE $Column_StudentSurveyRespondTable_PublishSurveyID = ${it.Id} AND $Column_StudentSurveyRespondTable_QuestionID=$questionID AND $Column_StudentSurveyRespondTable_AnswerID=5"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
            val count = cursor.count
            cursor.close()
            return count.toFloat()
    }




}

    /* fun deleteExpiredPublishedSurveys() {
         val db: SQLiteDatabase = this.writableDatabase
         val sql = "DELETE FROM $PublishedSurveyTable WHERE  ${Column_PublishedSurvey_EndDate}<DATE()"
         db.execSQL(sql)
     }*/






