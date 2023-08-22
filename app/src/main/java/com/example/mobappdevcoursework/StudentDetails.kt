package com.example.mobappdevcoursework

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobappdevcoursework.model.DataBaseHelper
import com.example.mobappdevcoursework.model.Student

class StudentDetails : AppCompatActivity() {
    val databaseHelper: DataBaseHelper = DataBaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)
    }

    fun SearchButton(view: View) {
        val studentSearchIDField = findViewById<EditText>(R.id.editTextStudentSearchID)
        val studentSearchID = studentSearchIDField.text.toString()
        if (studentSearchID.isEmpty()) {
            Toast.makeText(this, "Please type a student ID.", Toast.LENGTH_SHORT).show()
            return
        }
        val studentID = studentSearchID.toInt()
        val student = databaseHelper.getStudentDetails(studentID) ?: return

        findViewById<EditText>(R.id.editTextStudentName).setText(student.username)
        findViewById<EditText>(R.id.editTextStudentPassword).setText(student.password)
        studentSearchIDField.isEnabled = false
        findViewById<Button>(R.id.buttonDeleteStudent).isEnabled = true
        findViewById<Button>(R.id.buttonUpdateStudent).isEnabled = true
        findViewById<Button>(R.id.buttonAddStudent).isEnabled = true
    }
    fun buttonNewStudent(view: View) {
        val studentNameField = findViewById<EditText>(R.id.editTextStudentName)
        val studentPasswordField = findViewById<EditText>(R.id.editTextStudentPassword)
        val studentSearchIDField = findViewById<EditText>(R.id.editTextStudentSearchID)
        if (studentNameField.text.isEmpty() && studentPasswordField.text.isEmpty() && studentSearchIDField.text.isEmpty()) {
            Toast.makeText(this, "Fields are empty.", Toast.LENGTH_SHORT).show()
            return
        }

        findViewById<Button>(R.id.buttonAddStudent).isEnabled = true
        findViewById<Button>(R.id.buttonDeleteStudent).isEnabled = false
        findViewById<Button>(R.id.buttonUpdateStudent).isEnabled = false
        studentNameField.text.clear()
        studentPasswordField.text.clear()
        studentSearchIDField.text.clear()
        studentSearchIDField.isEnabled = true
    }
    fun buttonAddStudent(view: View) {
        val studentUsername01 = findViewById<EditText>(R.id.editTextStudentName).text.toString()
        val studentPassword01 = findViewById<EditText>(R.id.editTextStudentPassword).text.toString()
        val student01 = Student(0, studentUsername01, studentPassword01)
        if (studentUsername01.isEmpty() || studentPassword01.isEmpty())
            Toast.makeText(this, "Please Enter Username and Password", Toast.LENGTH_LONG).show()
        else if (databaseHelper.addStudentDetails(student01)) {
            Toast.makeText(this, "Student details have been added successfully", Toast.LENGTH_SHORT).show()
            findViewById<EditText>(R.id.editTextStudentName).text.clear()
            findViewById<EditText>(R.id.editTextStudentPassword).text.clear()
        } else Toast.makeText(this, "Error: The student already existed", Toast.LENGTH_SHORT)
            .show()
    }
    fun buttonUpdateStudent(view: View) {
        val studentUsername01 = findViewById<EditText>(R.id.editTextStudentName).text.toString()
        val studentPassword01 = findViewById<EditText>(R.id.editTextStudentPassword).text.toString()
        val student01 = Student(0, studentUsername01, studentPassword01)
        if (studentUsername01.isEmpty() || studentPassword01.isEmpty())
            Toast.makeText(this, "Please Enter Username and Password", Toast.LENGTH_LONG).show()
        else if (databaseHelper.addStudentDetails(student01)) {
            Toast.makeText(this, "Student details have been added successfully", Toast.LENGTH_SHORT).show()
            findViewById<EditText>(R.id.editTextStudentName).text.clear()
            findViewById<EditText>(R.id.editTextStudentPassword).text.clear()
        } else Toast.makeText(this, "The student already existed", Toast.LENGTH_SHORT)
            .show()
    }
    fun buttonDeleteStudent(view: View) {
        val studentUserID03 = findViewById<EditText>(R.id.editTextStudentSearchID).text.toString().toInt()
        if (databaseHelper.deleteStudentDetails(Student(studentUserID03, " ", "")))
            Toast.makeText(this, "Student details deleted successfully", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "The student already has been deleted", Toast.LENGTH_SHORT).show()
    }
}