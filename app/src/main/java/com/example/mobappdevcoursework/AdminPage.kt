package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Admin
import kotlinx.android.synthetic.main.activity_adminpage.*

class AdminPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminpage)

        adminButtonLogin.setOnClickListener {
            val intent = Intent(this@AdminPage, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun buttonLogIn2(view: View) {
        val administerName = findViewById<EditText>(R.id.AndinUserName1).text.toString()
        val administerPassword = findViewById<EditText>(R.id.AdminPassword1).text.toString()

        if (administerName.isEmpty() || administerPassword.isEmpty())
            Toast.makeText(this, "Insert your username and password", Toast.LENGTH_LONG).show()
        else {
            val myDataBase = DataBaseHelper(this)
            val result = myDataBase.getAdmin(Admin(-1, administerName, administerPassword))

            if (result == -1)
                Toast.makeText(this, "Admin not found. Please try again", Toast.LENGTH_LONG)
                    .show()
            else if (result == -2)
                Toast.makeText(this, "The app can not open database", Toast.LENGTH_LONG).show()
            else intent = Intent(this, AdminPanelPage::class.java)
            startActivity(intent)
        }

    }
}