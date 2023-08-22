package com.example.mobappdevcoursework.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobappdevcoursework.model.*

private val DataBaseName = "AppDataBase.db"
private val ver: Int = 1

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, DataBaseName, null, ver) {

    /*Admin Table*/
    private val AdminTableName = "Admin"
    private val Column_AdminID = "Id"
    private val Column_AdminLoginName = "LoginName"
    private val Column_AdminPassWord = "PassWord"

    /*PublishSurvey Table*/
    private val PublishedTableName = "Published"
    private val Column_PublishedID = "Id"
    private val Column_PublishedTitle = "SurveyTitle"
    private val Column_PublishedDate = "Date"

    /*Student Table*/
    private val StudentTableName = "Student"
    private val Column_StudentID = "StudentID"
    private val Column_StudentLoginName = "LogIn"
    private val Column_StudentPassWord = "Password"

    /*Survey Table*/
    private val SurveyTableName = "Survey"
    private val Column_SurveyID = "Id"
    private val Column_SurveyTitle = "Title"
    private val Column_SurveyQuestion01 = "Question1"
    private val Column_SurveyQuestion02 = "Question2"
    private val Column_SurveyQuestion03 = "Question3"
    private val Column_SurveyQuestion04 = "Question4"
    private val Column_SurveyQuestion05 = "Question5"
    private val Column_SurveyQuestion06 = "Question6"
    private val Column_SurveyQuestion07 = "Question7"
    private val Column_SurveyQuestion08 = "Question8"
    private val Column_SurveyQuestion09 = "Question9"
    private val Column_SurveyQuestion010 = "Question10"

    /*Student Survey Respond*/
    private val StudentSurveyRespondTableName = "StudentSurveyRespond"
    private val Column_SSR_ID = "Id"
    private val Column_SSR_StudentId = "StudentId"
    private val Column_SSR_PublishedSurveyId = "PublishedSurveyId"
    private val Column_SSR_SurveyId = "SurveyId"
    private val Column_SSR_AnswerId = "Answer"
    private val Column_SSR_QuestionNum = "QuestionNum"




    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + StudentTableName + " ( " + Column_StudentID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_StudentLoginName + " TEXT NOT NULL UNIQUE, " +
                    Column_StudentPassWord + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE" + AdminTableName + " ( " + Column_AdminID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_AdminLoginName + " TEXT NOT NULL UNIQUE, " +
                    Column_AdminPassWord + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement ="CREATE TABLE" + SurveyTableName + " ( " + Column_SurveyID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_SurveyTitle + " TEXT NOT NULL , " +
                    Column_SurveyQuestion01 + " TEXT NOT NULL , " + Column_SurveyQuestion02 + " TEXT NOT NULL , " +
                    Column_SurveyQuestion03 + " TEXT NOT NULL , " + Column_SurveyQuestion04 + " TEXT NOT NULL , " +
                    Column_SurveyQuestion05 + " TEXT NOT NULL , " + Column_SurveyQuestion06 + " TEXT NOT NULL , " +
                    Column_SurveyQuestion07 + " TEXT NOT NULL , " + Column_SurveyQuestion08 + " TEXT NOT NULL , " +
                    Column_SurveyQuestion09 + " TEXT NOT NULL , " + Column_SurveyQuestion010 + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement ="CREATE TABLE" + PublishedTableName + " ( " + Column_PublishedID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_PublishedTitle + " TEXT NOT NULL , " +
                    Column_PublishedDate + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement ="CREATE TABLE" + StudentSurveyRespondTableName + " ( " + Column_SSR_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_SSR_PublishedSurveyId + " INTEGER NOT NULL, " +
                    Column_SSR_AnswerId + " TEXT NOT NULL, " + Column_SSR_SurveyId + " INTEGER NOT NULL, " +
                    Column_SSR_StudentId + " INTEGER NOT NULL" + Column_SSR_QuestionNum+"INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement)

        }
        catch (e: SQLiteException) {}

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }



    fun addStudent(student: Student) : Int {

        val isUserNameAlreadyExists = checkStudentName(student)
        if(isUserNameAlreadyExists < 0)
            return isUserNameAlreadyExists

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_StudentLoginName, student.username.lowercase())
        cv.put(Column_StudentPassWord , student.password)

        val success  =  db.insert(StudentTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt()
        else return 1

    }

    private fun checkStudentName(student: Student): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = student.username.lowercase()

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLoginName = ?"
        val param = arrayOf(userName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3
        }

        cursor.close()
        db.close()
        return 0

    }

    fun getStudent(student: Student) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = student.username.lowercase()
        val userPassword = student.password

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLoginName = ? AND $Column_StudentPassWord = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
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

    fun getAdmin(Admin: Admin) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val administerName = Admin.username.lowercase()
        val administerPassword = Admin.password

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Column_AdminLoginName = ? AND $Column_AdminPassWord = ?"
        val param = arrayOf(administerName,administerPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
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

    fun getStudentDetails(eID: Int) : Student {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentID = $eID"

        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst()){
            db.close()
            return Student(cursor.getInt(0), cursor.getString(1),cursor.getString(2))
        }
        else  {
            db.close()
            return Student( 0, "Student does not exist", "Student does not exist") // not found
        }
    }

    fun getStudentDetails(eUsername: String): Student {
        val db = this.readableDatabase
        val selection = "$Column_StudentLoginName = ?"
        val selectionArgs = arrayOf(eUsername)
        val cursor = db.query(
            StudentTableName,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            db.close()
            return Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
        } else {
            db.close()
            return Student(0, "Student does not exist", "Student does not exist") // not found
        }
    }



    fun addStudentDetails(student: Student) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_StudentLoginName, student.username)
        cv.put(Column_StudentPassWord,student.password)

        val success  =  db.insert(StudentTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun updateStudentDetails(student: Student) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_StudentLoginName, student.username)
        cv.put(Column_StudentPassWord,student.password)

        val result = db.update(StudentTableName,cv,"$Column_StudentID = ${student.stuID}", null) == 1
        db.close()
        return result
    }

    fun deleteStudentDetails(student: Student) : Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(StudentTableName, "$Column_StudentID = ${student.stuID}", null) == 1

        db.close()
        return result
    }

    fun addSurvey(survey: Survey) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_SurveyTitle, survey.surTitle)
        cv.put(Column_SurveyQuestion01,survey.question1)
        cv.put(Column_SurveyQuestion02,survey.question2)
        cv.put(Column_SurveyQuestion03,survey.question3)
        cv.put(Column_SurveyQuestion04,survey.question4)
        cv.put(Column_SurveyQuestion05,survey.question5)
        cv.put(Column_SurveyQuestion06,survey.question6)
        cv.put(Column_SurveyQuestion07,survey.question7)
        cv.put(Column_SurveyQuestion08,survey.question8)
        cv.put(Column_SurveyQuestion09,survey.question9)
        cv.put(Column_SurveyQuestion010,survey.question10)

        val success  =  db.insert(SurveyTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getSurvey(eID: Int) : Survey {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName WHERE $Column_SurveyID = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement,null)
        if (cursor.moveToFirst()){
            db.close()
            return Survey(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                , cursor.getString(9), cursor.getString(10), cursor.getString(11))
        }
        else {
            db.close()
            return Survey(0,"Survey does not exist", "Question 1 does not exist",
                "Question 2 does not exist", "Question 3 does not exist",
                "Question 4 does not exist", "Question 5 does not exist",
                "Question 6 does not exist", "Question 7 does not exist",
                "Question 8 does not exist", "Question 9 does not exist",
                "Question 10 does not exist")
        }
    }

    fun getAllSurvey() : ArrayList<Survey> {
        val SurveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst())
            do {
                val survey = Survey(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                    , cursor.getString(9), cursor.getString(10), cursor.getString(11))
                SurveyList.add(survey)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return SurveyList
    }


    fun getSurveyFromName(name: String) : Survey {
        return getAllSurvey().first{it.surTitle == name};
    }

    fun updateSurvey(survey: Survey) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_SurveyTitle, survey.surTitle)
        cv.put(Column_SurveyQuestion01,survey.question1)
        cv.put(Column_SurveyQuestion02,survey.question2)
        cv.put(Column_SurveyQuestion03,survey.question3)
        cv.put(Column_SurveyQuestion04,survey.question4)
        cv.put(Column_SurveyQuestion05,survey.question5)
        cv.put(Column_SurveyQuestion06,survey.question6)
        cv.put(Column_SurveyQuestion07,survey.question7)
        cv.put(Column_SurveyQuestion08,survey.question8)
        cv.put(Column_SurveyQuestion09,survey.question9)
        cv.put(Column_SurveyQuestion010,survey.question10)

        val result = db.update(SurveyTableName,cv,"$Column_SurveyID = ${survey.surID}", null) == 1
        db.close()
        return result
    }

    fun deleteSurvey(survey: Survey) : Boolean{

        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(SurveyTableName, "$Column_SurveyID = ${survey.surID}", null) == 1

        db.close()
        return result
    }

    fun deletePublished(published: Published) : Boolean{

        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(PublishedTableName, "$Column_PublishedID = ${published.pubID}", null) == 1

        db.close()
        return result
    }

    fun addPublished(published: Published) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_PublishedTitle, published.surTitle)
        cv.put(Column_PublishedDate, published.date)

        val success  =  db.insert(PublishedTableName, null, cv)
        db.close()
        return success != -1L
    }


    fun getAllPublished(): ArrayList<Published> {
        val publishedList = ArrayList<Published>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $PublishedTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)
                val date: String = cursor.getString(2)

                val pub = Published(id, title, date)
                publishedList.add(pub)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return publishedList
    }

    fun addAnswer(surveyRespond: SurveyResponse) : Boolean{
        var db: SQLiteDatabase = this.writableDatabase
        var cv: ContentValues = ContentValues()
        val sqlStatement = "SELECT * FROM $StudentSurveyRespondTableName WHERE $Column_SSR_SurveyId = ${surveyRespond.surID} AND $Column_SSR_PublishedSurveyId = " +
                "${surveyRespond.PublishSurveyID} AND $Column_SSR_StudentId = ${surveyRespond.stuID} AND $Column_SSR_QuestionNum = ${surveyRespond.queNumber}"
        val cursor: Cursor = db.rawQuery(sqlStatement,null)

        if(cursor.count <= 0) {
            db = this.writableDatabase

            cv.put(Column_SSR_PublishedSurveyId, surveyRespond.PublishSurveyID)
            cv.put(Column_SSR_SurveyId, surveyRespond.surID)
            cv.put(Column_SSR_AnswerId, surveyRespond.ans)
            cv.put(Column_SSR_QuestionNum, surveyRespond.queNumber)
            cv.put(Column_SSR_StudentId, surveyRespond.stuID)

            val success  =  db.insert(StudentSurveyRespondTableName, null, cv)
            db.close()
            return success != -1L
        }else{
            db.close()
            return 1 !=1
        }
    }
    fun getSurveyResponse(survey: Survey, published: Published): ArrayList<SurveyResponse> {

        val surveyResponseList = ArrayList<SurveyResponse>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyRespondTableName WHERE $Column_SSR_SurveyId = ${survey.surID} AND $Column_SSR_PublishedSurveyId = ${published.pubID}"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val ID: Int = cursor.getInt(0)
                val surID: Int = cursor.getInt(2)
                val PublishSurveyID: Int = cursor.getInt(1)
                val ans: String = cursor.getString(3)
                val queNumber: Int = cursor.getInt(4)
                val stuID: Int = cursor.getInt(5)

                val response = SurveyResponse(ID,stuID,PublishSurveyID,surID,ans,queNumber)
                surveyResponseList.add(response)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyResponseList
    }

}