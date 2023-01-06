package com.iiddd.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart: Button = findViewById(R.id.buttonStart)
        val nameInput: TextInputEditText = findViewById(R.id.nameInput)
        buttonStart.setOnClickListener {
            if (nameInput.text!!.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your name",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}