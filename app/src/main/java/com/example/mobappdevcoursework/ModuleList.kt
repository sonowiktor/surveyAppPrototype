package com.example.mobappdevcoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Survey
import com.example.mobappdevcoursework.model.Published

class ModuleList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modulelist)


        recyclerView = findViewById(R.id.recycleViewQuestions)
        databaseHelper = DataBaseHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = SurveyAdapter();
        recyclerView.adapter = adapter
        adapter?.addItems(databaseHelper.getAllPublished())
        adapter.setOnItemClick(object : SurveyAdapter.SurveyInterface{
            override fun onSurveySelect(surTitle: String) {
                Toast.makeText(this@ModuleList, surTitle, Toast.LENGTH_SHORT).show();
                var survey: Survey = databaseHelper.getSurveyFromName(surTitle)
                var published: Published = databaseHelper.getAllPublished().first{ a -> a.surTitle == surTitle}

                val intent = Intent(this@ModuleList, SurveyQuestion::class.java)
                if(getIntent().getExtras() != null){
                    intent.putExtra("Survey", survey)
                    intent.putExtra("Publish", published)
                    intent.putExtra("StudentId", getIntent().getSerializableExtra("StudentId"))
                }

                startActivity(intent)
            }

        })
    }

    fun LogOutStudentButton(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}