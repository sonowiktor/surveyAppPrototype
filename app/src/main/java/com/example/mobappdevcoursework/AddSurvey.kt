package com.example.mobappdevcoursework

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Survey

class AddSurvey : AppCompatActivity() {
    val databaseHelper: DataBaseHelper = DataBaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addsurvey)
    }

    fun addSurveyQuestionButton(view: View) {
        val SurveyTitle = findViewById<EditText>(R.id.newAddSurveyName).text.toString()
        val SurveyQuestion01 = findViewById<EditText>(R.id.Question1).text.toString()
        val SurveyQuestion02 = findViewById<EditText>(R.id.Question2).text.toString()
        val SurveyQuestion03 = findViewById<EditText>(R.id.Question3).text.toString()
        val SurveyQuestion04 = findViewById<EditText>(R.id.Question4).text.toString()
        val SurveyQuestion05 = findViewById<EditText>(R.id.Question5).text.toString()
        val SurveyQuestion06 = findViewById<EditText>(R.id.Question6).text.toString()
        val SurveyQuestion07 = findViewById<EditText>(R.id.Question7).text.toString()
        val SurveyQuestion08 = findViewById<EditText>(R.id.Question8).text.toString()
        val SurveyQuestion09 = findViewById<EditText>(R.id.Question9).text.toString()
        val SurveyQuestion010 = findViewById<EditText>(R.id.Question10).text.toString()

        val surveyQues = Survey(
            0,
            SurveyTitle,
            SurveyQuestion01,
            SurveyQuestion02,
            SurveyQuestion03,
            SurveyQuestion04,
            SurveyQuestion05,
            SurveyQuestion06,
            SurveyQuestion07,
            SurveyQuestion08,
            SurveyQuestion09,
            SurveyQuestion010
        )

        if (SurveyTitle.isEmpty() || SurveyQuestion01.isEmpty() || SurveyQuestion02.isEmpty()
            || SurveyQuestion03.isEmpty() || SurveyQuestion04.isEmpty() || SurveyQuestion05.isEmpty()
            || SurveyQuestion06.isEmpty() || SurveyQuestion07.isEmpty() || SurveyQuestion08.isEmpty()
            || SurveyQuestion09.isEmpty() || SurveyQuestion010.isEmpty())

            Toast.makeText(this, "All the fields have to be filled in", Toast.LENGTH_LONG).show()

        else if (databaseHelper.addSurvey(surveyQues)) {
            Toast.makeText(
                this,
                "Survey is added",
                Toast.LENGTH_SHORT
            ).show()
            findViewById<EditText>(R.id.newAddSurveyName).text.clear()
            findViewById<EditText>(R.id.Question1).text.clear()
            findViewById<EditText>(R.id.Question2).text.clear()
            findViewById<EditText>(R.id.Question3).text.clear()
            findViewById<EditText>(R.id.Question4).text.clear()
            findViewById<EditText>(R.id.Question5).text.clear()
            findViewById<EditText>(R.id.Question6).text.clear()
            findViewById<EditText>(R.id.Question7).text.clear()
            findViewById<EditText>(R.id.Question8).text.clear()
            findViewById<EditText>(R.id.Question9).text.clear()
            findViewById<EditText>(R.id.Question10).text.clear()
        }

        else Toast.makeText(this, "Survey already exists", Toast.LENGTH_SHORT).show()
    }
}