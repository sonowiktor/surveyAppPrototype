package com.example.mobappdevcoursework

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Survey
import com.example.mobappdevcoursework.model.SurveyResponse
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF


class SurveyStats : AppCompatActivity() {
    private lateinit var pieChart: PieChart;
    private lateinit var surveyResponseList:ArrayList<SurveyResponse>
    private var currentQuestion = 1;

    private lateinit var textQuestion: TextView;
    private lateinit var textQuestionNumber: TextView;
    private lateinit var buttonNext: Button;
    private lateinit var buttonPrevious: Button;
    private lateinit var databaseHelper: DataBaseHelper

    private lateinit var survey: Survey;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surveystats)

        databaseHelper = DataBaseHelper(this)
        init();
        load();
    }
    fun init(){
        pieChart = findViewById<PieChart>(R.id.pieChart)
        textQuestionNumber = findViewById<TextView>(R.id.txtQuestion)
        textQuestionNumber = findViewById<TextView>(R.id.txtQNum)
        buttonNext = findViewById<Button>(R.id.btnNext)
        buttonPrevious = findViewById<Button>(R.id.btnPrev)

        if(getIntent().getExtras() != null && (getIntent().getSerializableExtra("SurveyResponses") as ArrayList<SurveyResponse>) != null){
            surveyResponseList = getIntent().getSerializableExtra("SurveyResponses") as ArrayList<SurveyResponse>
            survey = getIntent().getSerializableExtra("Survey") as Survey
        }else{
            Toast.makeText(this@SurveyStats, "Unable to view responses", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SurveyStats, AdminPanelPage::class.java))
        }
    }

    private fun load(){
        buttonPrevious.isEnabled = true
        val result = java.util.ArrayList<PieEntry>()
        val questionResults = surveyResponseList.filter { r -> r.queNumber == currentQuestion };

        pieChart.centerText = "Responses"
        pieChart.setDrawEntryLabels(false)

        result.add(PieEntry(questionResults.filter { r -> r.ans == "Strongly Agree"}.size.toFloat(),"Strongly Agree" ))
        result.add(PieEntry(questionResults.filter { r -> r.ans == "Agree"}.size.toFloat(),"Agree" ))
        result.add(PieEntry(questionResults.filter { r -> r.ans == "Neither Agree nor Disagree"}.size.toFloat(),"Neither Agree nor Disagree" ))
        result.add(PieEntry(questionResults.filter { r -> r.ans == "Disagree"}.size.toFloat(),"Disagree" ))
        result.add(PieEntry(questionResults.filter { r -> r.ans == "Strongly Disagree"}.size.toFloat(),"Strongly Disagree" ))

        textQuestionNumber.text = "$currentQuestion/10"
        when(currentQuestion){
            1 ->  {
                textQuestion.text = survey.question1;
                buttonPrevious.isEnabled = false
            };
            2 -> textQuestion.text = survey.question2;
            3 -> textQuestion.text = survey.question3;
            4 -> textQuestion.text = survey.question4;
            5 -> textQuestion.text = survey.question5;
            6 -> textQuestion.text = survey.question6;
            7 -> textQuestion.text = survey.question7;
            8 -> textQuestion.text = survey.question8;
            9 -> textQuestion.text = survey.question9;
            10 -> {
                textQuestion.text = survey.question10;
                buttonNext.text  = "Finish";
            }
            11 ->{
                Toast.makeText(this@SurveyStats, "All Question viewed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SurveyStats, AdminPanelPage::class.java))
            }
            else ->{
                Toast.makeText(this@SurveyStats, "Unable to view question", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SurveyStats, AdminPanelPage::class.java))
            }
        }

        val dataSet = PieDataSet(result, "Question $currentQuestion")

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.stronglyAgree))
        colors.add(resources.getColor(R.color.agree))
        colors.add(resources.getColor(R.color.notSure))
        colors.add(resources.getColor(R.color.disagree))
        colors.add(resources.getColor(R.color.stronglyDisagree))
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.setData(data)
        pieChart.highlightValues(null)
        pieChart.animateY(1000);
        pieChart.invalidate()
    }

    fun PreviousQ(view: View) {
        currentQuestion--;
        load();
    }
    fun btnNext(view: View) {
        currentQuestion++;
        load();
    }
}