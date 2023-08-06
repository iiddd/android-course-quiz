package com.iiddd.quiz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.iiddd.quiz.R
import com.iiddd.quiz.common.Constants.USER_NAME
//import com.iiddd.quiz.ui.quiz.QuizQuestionsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var name: String? = null
    private var buttonStart: Button? = null
    private var nameInput: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        initScreen()

//        buttonStart?.setOnClickListener {
//            if (nameInput?.text!!.isEmpty()) {
//                Toast.makeText(
//                    this,
//                    "Please enter your name",
//                    Toast.LENGTH_LONG
//                ).show()
//            } else {
//                name = nameInput?.text.toString()
//                hideKeyboard()
//                val intent = Intent(this, QuizQuestionsActivity::class.java)
//                intent.putExtra(USER_NAME, name)
//                startActivity(intent)
//                finish()
//            }
//        }
    }
//
//    private fun initScreen() {
//        setContentView(R.layout.activity_main)
//        buttonStart = findViewById(R.id.btn_welcome_start)
//        nameInput = findViewById(R.id.et_welcome_name)
//    }
//
//    private fun hideKeyboard() {
//        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
//        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//    }
}