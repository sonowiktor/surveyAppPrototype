package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Published
import com.example.mobappdevcoursework.model.Survey

class ModuleView : AppCompatActivity(){
    private lateinit var buttonRefreshStudent: Button
    private lateinit var recyclerView: RecyclerView

    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moduleview)

        initView()
        databaseHelper = DataBaseHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = SurveyAdapter();
        recyclerView.adapter = adapter
        adapter.setOnItemClick(object : SurveyAdapter.SurveyInterface{
            override fun onSurveySelect(surveyTitle: String) {
                Toast.makeText(this@ModuleView, surveyTitle, Toast.LENGTH_SHORT).show();
                var survey: Survey = databaseHelper.getSurveyFromName(surveyTitle)
                var published: Published = databaseHelper.getAllPublished().first{ a -> a.surTitle == surveyTitle}

                val intent = Intent(this@ModuleView, ViewResult::class.java)
                intent.putExtra("Survey", survey)
                intent.putExtra("Publish", published)
                startActivity(intent)
            }

        })
        adapter?.addItems(databaseHelper.getAllPublished())
        buttonRefreshStudent.setOnClickListener {adapter?.addItems(databaseHelper.getAllPublished())}
    }

    private fun initView() {
        buttonRefreshStudent = findViewById(R.id.btnRefreshStudent)
        recyclerView = findViewById(R.id.recycleViewQuestions)

    }


}