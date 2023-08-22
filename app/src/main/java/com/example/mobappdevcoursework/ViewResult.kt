package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Published
import com.example.mobappdevcoursework.model.Survey
import com.example.mobappdevcoursework.model.SurveyResponse

class ViewResult : AppCompatActivity() {
    private lateinit var textSurveyTitle: TextView;
    private lateinit var textSurveyParticipants: TextView;
    private lateinit var buttonViewStats: TextView;
    private lateinit var survey: Survey;
    private lateinit var published: Published;

    private lateinit var databaseHelper: DataBaseHelper
    private lateinit var surveyResponseList:ArrayList<SurveyResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewresult)

        databaseHelper = DataBaseHelper(this)

        init()

        if(getIntent().getExtras() != null){
            survey = getIntent().getSerializableExtra("Survey") as Survey
            published = getIntent().getSerializableExtra("Publish") as Published

            surveyResponseList = databaseHelper.getSurveyResponse(survey, published)
            textSurveyTitle.text = survey.surTitle
            if(surveyResponseList.size % 10 == 0){
                textSurveyParticipants.text = (surveyResponseList.size / 10).toString();
            }else{
                textSurveyParticipants.text = ((surveyResponseList.size - (surveyResponseList.size % 10)) / 10).toString();
            }
        }

        buttonViewStats.setOnClickListener {
            if(surveyResponseList != null){
                val intent = Intent(this@ViewResult, SurveyStats::class.java)
                intent.putExtra("SurveyResponses", surveyResponseList)
                intent.putExtra("Survey", survey)
                startActivity(intent)
            }else{
                Toast.makeText(this@ViewResult, "Invalid request", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun init(){
        textSurveyTitle = findViewById<TextView>(R.id.txtSurveyTitle)
        textSurveyParticipants = findViewById<TextView>(R.id.txtSurveyParticipants)
        buttonViewStats = findViewById<TextView>(R.id.btnViewStatistics)
    }
}