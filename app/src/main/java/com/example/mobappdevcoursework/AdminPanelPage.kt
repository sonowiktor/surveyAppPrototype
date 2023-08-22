package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adminmainpage.*

class AdminPanelPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminmainpage)

        buttonEditSurvey.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, EditSurvey::class.java)
            startActivity(intent)
        }

        buttonStudentDetails.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, StudentDetails::class.java)
            startActivity(intent)
        }

        buttonAddSurvey.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, AddSurvey::class.java)
            startActivity(intent)
        }

        buttonAdminLogOut.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, AdminPage::class.java)
            startActivity(intent)
        }

        buttonPublishSurvey.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, PublishSurvey::class.java)
            startActivity(intent)
        }

        buttonViewGraph.setOnClickListener {
            val intent = Intent(this@AdminPanelPage, SurveyStats::class.java)
            startActivity(intent)
        }

    }
}