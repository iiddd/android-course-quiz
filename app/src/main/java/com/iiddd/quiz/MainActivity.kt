package com.iiddd.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                hideKeyboard()
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}