package com.example.mobappdevcoursework

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ThankYou : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var top: Animation
    private lateinit var bottom: Animation
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)


        val intent = Intent(this, ModuleList::class.java)
        startActivity(intent)
        finish()
    }
}