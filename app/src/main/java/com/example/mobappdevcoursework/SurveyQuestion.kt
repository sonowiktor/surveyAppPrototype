package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Published
import com.example.mobappdevcoursework.model.Survey
import com.example.mobappdevcoursework.model.SurveyResponse

class SurveyQuestion : AppCompatActivity() {
    private lateinit var TV_question : TextView;
    private lateinit var radioGroupAnswer : RadioGroup;
    private lateinit var buttonNext : Button;
    val databaseHelper: DataBaseHelper = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)

        init();
    }

    fun init(){
        TV_question = findViewById<TextView>(R.id.textViewGetQuestion1)
        radioGroupAnswer = findViewById<RadioGroup>(R.id.radioGroupAnswer)
        buttonNext = findViewById<Button>(R.id.btnNextQuestion1)

        var survey: Survey? = null;
        var published: Published? = null;

        if(getIntent().getExtras() != null){
            survey = getIntent().getSerializableExtra("Survey") as Survey
            published = getIntent().getSerializableExtra("Publish") as Published

            TV_question.text = survey.question1;
        }

        buttonNext.setOnClickListener{
            val selected:Int = radioGroupAnswer.checkedRadioButtonId;
            if(selected != -1){
                var answer: RadioButton = findViewById(selected)

                val intent = Intent(this@SurveyQuestion, SurveyQuestion2::class.java)

                databaseHelper.addAnswer(
                    SurveyResponse(-1, getIntent().getSerializableExtra("StudentId") as Int,
                        (getIntent().getSerializableExtra("Publish") as Published).pubID,
                        (getIntent().getSerializableExtra("Survey") as Survey).surID, answer.text.toString(), 1)
                )

                intent.putExtra("Survey", survey)
                intent.putExtra("Publish", (getIntent().getSerializableExtra("Publish") as Published))
                intent.putExtra("StudentId", getIntent().getSerializableExtra("StudentId"))
                startActivity(intent)
            }else{
                Toast.makeText(this@SurveyQuestion, "Select option", Toast.LENGTH_SHORT).show()
            }

        }



    }
}