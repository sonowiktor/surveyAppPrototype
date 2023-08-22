package com.example.mobappdevcoursework

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.util.Pair
import com.example.mobappdevcoursework.model.Published

class PublishSurvey : AppCompatActivity() {
    val databaseHelper: DataBaseHelper = DataBaseHelper(this)
    private lateinit var text_date: TextView
    private lateinit var buttonCalendar: MaterialButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publishsurvey)

        text_date =findViewById(R.id.textViewClickStart)
        buttonCalendar = findViewById(R.id.buttonStartEndDate)

        buttonCalendar.setOnClickListener {
            val datePickerRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select the date: ")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.thisMonthInUtcMilliseconds()
                    )
                )
                .build()
            datePickerRange.show(supportFragmentManager, "date_picker")

            datePickerRange.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                text_date.text = "${simpleDateFormat.format(it.first)} / ${simpleDateFormat.format(it.second)}"
            }
        }
    }

    fun buttonTitleSurveyFind(view: View) {
        val surveyID = findViewById<EditText>(R.id.editTextPublishSearchID).text.toString()
        if (surveyID.isEmpty()) {
            Toast.makeText(this, "Please enter a survey ID.", Toast.LENGTH_SHORT).show()
            return
        }
        val questionID = surveyID.toInt()
        val survey = databaseHelper.getSurvey(questionID) ?: return

        findViewById<EditText>(R.id.textViewSurveyPublish).setText(survey.surTitle)
        findViewById<EditText>(R.id.editTextPublishSearchID).isEnabled = true
    }

    fun buttonPublishSurvey(view: View) {

        val publishedsurTle = findViewById<EditText>(R.id.textViewSurveyPublish).text.toString()
        val publishedsurdate = findViewById<EditText>(R.id.textViewClickStart).text.toString()

        val published001 = Published(0, publishedsurTle, publishedsurdate)

        if (publishedsurTle.isEmpty() || publishedsurdate.isEmpty())

            Toast.makeText(this, "Please Select Survey and Publish Date!!", Toast.LENGTH_LONG).show()

        else if (databaseHelper.addPublished(published001)) {
            Toast.makeText(
                this,
                "Survey is Published",
                Toast.LENGTH_SHORT
            ).show()
            findViewById<EditText>(R.id.textViewSurveyPublish).text.clear()
            findViewById<EditText>(R.id.textViewClickStart).text.clear()
        }

        else Toast.makeText(this, "Error: Survey is already published", Toast.LENGTH_SHORT).show()
    }

}