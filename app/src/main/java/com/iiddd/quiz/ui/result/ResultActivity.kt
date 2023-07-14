package com.iiddd.quiz.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.iiddd.quiz.common.Constants.CORRECT_ANSWERS
import com.iiddd.quiz.common.Constants.TOTAL_QUESTION
import com.iiddd.quiz.common.Constants.USER_NAME
import com.iiddd.quiz.R
import com.iiddd.quiz.ui.MainActivity

class ResultActivity : AppCompatActivity() {

    private var name: TextView? = null
    private var score: TextView? = null
    private var finishButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initScreen()
        setUserResults()
        setFinishButton()
    }

    private fun setUserResults() {
        val correctQuestions = intent.getIntExtra(CORRECT_ANSWERS, 0)
        val totalQuestion = intent.getIntExtra(TOTAL_QUESTION, 0)
        name?.text = intent.getStringExtra(USER_NAME)
        score?.text = getString(R.string.result_score, correctQuestions, totalQuestion)
    }

    private fun initScreen() {
        name = findViewById(R.id.tv_result_name)
        score = findViewById(R.id.tv_result_score)
        finishButton = findViewById(R.id.btn_finish_button)
    }

    private fun setFinishButton() {
        finishButton?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}