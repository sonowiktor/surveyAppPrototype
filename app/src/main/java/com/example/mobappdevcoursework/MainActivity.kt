package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Student
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var databaseHelper: DataBaseHelper = DataBaseHelper(this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener {
            buttonSignUp.background = resources.getDrawable(R.drawable.ic_switch_tracks, null)
            buttonSignUp.setTextColor(resources.getColor(R.color.white, null))
            buttonLogIn.background = null
            signInLayout.visibility = View.VISIBLE
            loginInLayout.visibility = View.GONE

            buttonLogIn.setTextColor(resources.getColor(R.color.black, null))
        }

        buttonLogIn.setOnClickListener {
            buttonLogIn.background = resources.getDrawable(R.drawable.ic_switch_tracks, null)
            buttonLogIn.setTextColor(resources.getColor(R.color.white, null))
            buttonSignUp.background = null

            loginInLayout.visibility = View.VISIBLE
            signInLayout.visibility = View.GONE

            buttonSignUp.setTextColor(resources.getColor(R.color.black, null))
        }

        adminButtonLogin.setOnClickListener {
            val intent = Intent(this@MainActivity, AdminPage::class.java)
            startActivity(intent)
        }
    }


    fun buttonSignUp2(view: View) {
        val signUpUsername = findViewById<EditText>(R.id.editTextEnterUsername).text.toString()
        val signUpPassword = findViewById<EditText>(R.id.editTextEnterPassword).text.toString()
        val signUpRepeatPassword = findViewById<EditText>(R.id.editTextConfirmPassword).text.toString()

        if (signUpUsername.isEmpty() || signUpPassword.isEmpty() || signUpRepeatPassword.isEmpty())
            Toast.makeText(this, "All the data are required", Toast.LENGTH_LONG).show()

        if (!signUpPassword.toString().isEmpty() && !signUpRepeatPassword.toString().isEmpty())
            Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_LONG).show()

        if (signUpPassword.toString() == signUpPassword.toString())
            Toast.makeText(this, "Passwords are matching", Toast.LENGTH_LONG).show()

        else {
            val myDataBase = DataBaseHelper(this)
            val newUser = Student(-1, signUpUsername, signUpPassword)
            val result = myDataBase.addStudent(newUser)

            when(result) {
                1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Congrats, you have successfully register", Toast.LENGTH_LONG).show()
                }
                -1 -> Toast.makeText(this, "There is a problem with creating an account", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(this, "Database can not be open", Toast.LENGTH_SHORT).show()
                -3 -> Toast.makeText(this, "This account already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun buttonLogIn2(view: View) {
        val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if (username.isEmpty() || password.isEmpty())
            Toast.makeText(this, "Please enter your username and password", Toast.LENGTH_LONG).show()

        else {
            val myDataBase = DataBaseHelper(this)
            val result = myDataBase.getStudent(Student(-1, username, password))

            if (result == -1)
                Toast.makeText(this, "Sorry, the user was not found", Toast.LENGTH_LONG).show()
            else if (result == -2)
                Toast.makeText(this, "Sorry, database do not want to open", Toast.LENGTH_LONG).show()
            else
                intent = Intent(this, ModuleList::class.java)

            val studentID: Int = databaseHelper.getStudentDetails(username).stuID
            intent.putExtra("studentID", studentID)
            startActivity(intent)

        }

    }
}