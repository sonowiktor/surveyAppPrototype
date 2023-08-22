package com.example.mobappdevcoursework

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Survey

class EditSurvey : AppCompatActivity() {
    val databaseHelper: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editsurvey)
    }

    fun buttonSearchSurvey(view: View) {
        val surveyID = findViewById<EditText>(R.id.editTextSurveyID).text.toString()
        if (surveyID.isEmpty()) {
            Toast.makeText(this, "Please type a survey ID.", Toast.LENGTH_SHORT).show()
            return
        }
        val questionID = surveyID.toInt()
        val survey = databaseHelper.getSurvey(questionID)

        findViewById<EditText>(R.id.newAddSurveyTitle).setText(survey.surTitle)
        findViewById<EditText>(R.id.editTextSurveyQuestion1).setText(survey.question1)
        findViewById<EditText>(R.id.editTextSurveyQuestion2).setText(survey.question2)
        findViewById<EditText>(R.id.editTextSurveyQuestion3).setText(survey.question3)
        findViewById<EditText>(R.id.editTextSurveyQuestion4).setText(survey.question4)
        findViewById<EditText>(R.id.editTextSurveyQuestion5).setText(survey.question5)
        findViewById<EditText>(R.id.editTextSurveyQuestion6).setText(survey.question6)
        findViewById<EditText>(R.id.editTextSurveyQuestion7).setText(survey.question7)
        findViewById<EditText>(R.id.editTextSurveyQuestion8).setText(survey.question8)
        findViewById<EditText>(R.id.editTextSurveyQuestion9).setText(survey.question9)
        findViewById<EditText>(R.id.editTextSurveyQuestion10).setText(survey.question10)
        findViewById<Button>(R.id.updateSurveyQuestionsButton).isEnabled = true
        findViewById<Button>(R.id.deleteSurveyQuestionsButton).isEnabled = true

    }
    fun updateSurveyQuestionsButton(view: View) {
        val surveyID = findViewById<EditText>(R.id.editTextSurveyID).text.toString()
        if (surveyID.isEmpty()) {
            Toast.makeText(this, "Please type a survey ID.", Toast.LENGTH_SHORT).show()
            return
        }
        val questionID = surveyID.toInt()
        val survey = databaseHelper.getSurvey(questionID) ?: return
        val surveySearchID = findViewById<EditText>(R.id.editTextSurveyID).text.toString().toInt()
        val surveyTitleQues = findViewById<EditText>(R.id.newAddSurveyTitle).text.toString()
        val surveyQues01 = findViewById<EditText>(R.id.editTextSurveyQuestion1).text.toString()
        val surveyQues02 = findViewById<EditText>(R.id.editTextSurveyQuestion2).text.toString()
        val surveyQues03 = findViewById<EditText>(R.id.editTextSurveyQuestion3).text.toString()
        val surveyQues04 = findViewById<EditText>(R.id.editTextSurveyQuestion4).text.toString()
        val surveyQues05 = findViewById<EditText>(R.id.editTextSurveyQuestion5).text.toString()
        val surveyQues06 = findViewById<EditText>(R.id.editTextSurveyQuestion6).text.toString()
        val surveyQues07 = findViewById<EditText>(R.id.editTextSurveyQuestion7).text.toString()
        val surveyQues08 = findViewById<EditText>(R.id.editTextSurveyQuestion8).text.toString()
        val surveyQues09 = findViewById<EditText>(R.id.editTextSurveyQuestion9).text.toString()
        val surveyQues010 = findViewById<EditText>(R.id.editTextSurveyQuestion10).text.toString()

        if (databaseHelper.updateSurvey(
                Survey(surveySearchID, surveyTitleQues, surveyQues01, surveyQues02,
                    surveyQues03, surveyQues04, surveyQues05, surveyQues06, surveyQues07, surveyQues08,
                    surveyQues09, surveyQues010)
            ))
            Toast.makeText(this, "Survey has been updated", Toast.LENGTH_SHORT).show()

        else Toast.makeText(this, "Survey already exists", Toast.LENGTH_SHORT).show()

    }
    fun deleteSurveyQuestionsButton(view: View) {
        val surveyID = findViewById<EditText>(R.id.editTextSurveyID).text.toString()
        if (surveyID.isEmpty()) {
            Toast.makeText(this, "Please type a survey ID.", Toast.LENGTH_SHORT).show()
            return
        }

        val questionID = surveyID.toInt()
        val survey = databaseHelper.getSurvey(questionID) ?: return
        val published = databaseHelper.getAllPublished().first { it.surTitle == survey.surTitle }

        if (databaseHelper.deleteSurvey(
                Survey(questionID, " ", " ", " ",
                " ", " ", " ", " ", " ", " ",
                " ", " ")
            ) && databaseHelper.deletePublished(published)) {
            Toast.makeText(this, "The survey has been deleted", Toast.LENGTH_SHORT).show()
            findViewById<EditText>(R.id.newAddSurveyTitle).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion1).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion2).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion3).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion4).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion5).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion6).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion7).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion8).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion9).text.clear()
            findViewById<EditText>(R.id.editTextSurveyQuestion10).text.clear()

        }
        else{
            Toast.makeText(this, "Error: The survey already existed", Toast.LENGTH_SHORT).show()
        }}
}