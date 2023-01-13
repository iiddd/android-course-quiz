package com.iiddd.quiz

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var progressText: TextView? = null
    private var questionText: TextView? = null
    private var flagImage: ImageView? = null

    private var btnOptionOne: Button? = null
    private var btnOptionTwo: Button? = null
    private var btnOptionThree: Button? = null
    private var btnOptionFour: Button? = null

    private var btnSubmitButton: Button? = null
    private var selectedAnswer: Int? = null
    private var question: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        questionText = findViewById(R.id.questionText)
        flagImage = findViewById(R.id.flagImage)
        btnOptionOne = findViewById(R.id.answerOption1)
        btnOptionTwo = findViewById(R.id.answerOption2)
        btnOptionThree = findViewById(R.id.answerOption3)
        btnOptionFour = findViewById(R.id.answerOption4)
        btnSubmitButton = findViewById(R.id.submit)

        setQuestion()

        btnOptionOne?.setOnClickListener {
            setButtonSelected(btnOptionOne as Button)
            setButtonDefault(btnOptionTwo as Button)
            setButtonDefault(btnOptionThree as Button)
            setButtonDefault(btnOptionFour as Button)
            selectedAnswer = 0
            btnSubmitButton?.isEnabled = true
        }

        btnOptionTwo?.setOnClickListener {
            setButtonSelected(btnOptionTwo as Button)
            setButtonDefault(btnOptionOne as Button)
            setButtonDefault(btnOptionThree as Button)
            setButtonDefault(btnOptionFour as Button)
            selectedAnswer = 1
            btnSubmitButton?.isEnabled = true
        }

        btnOptionThree?.setOnClickListener {
            setButtonSelected(btnOptionThree as Button)
            setButtonDefault(btnOptionOne as Button)
            setButtonDefault(btnOptionTwo as Button)
            setButtonDefault(btnOptionFour as Button)
            selectedAnswer = 2
            btnSubmitButton?.isEnabled = true
        }

        btnOptionFour?.setOnClickListener {
            setButtonSelected(btnOptionFour as Button)
            setButtonDefault(btnOptionOne as Button)
            setButtonDefault(btnOptionTwo as Button)
            setButtonDefault(btnOptionThree as Button)
            selectedAnswer = 3
            btnSubmitButton?.isEnabled = true
        }

        btnSubmitButton?.setOnClickListener {
            if (question!!.answerOptions[selectedAnswer!!].isCorrect) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setQuestion() {
        val questionList = Constants.getQuestions()
        Log.i("QuestionQuiz", "Question list size is ${questionList.size}")

        for (i in questionList) {
            Log.e("Questions", i.questionText)
        }

        var currentPosition = 2
        question = questionList[currentPosition - 1]
        progressBar?.progress = currentPosition
        flagImage?.setImageResource(question!!.image)
        progressText?.text = "$currentPosition / ${progressBar?.max}"
        questionText?.text = question!!.questionText
        btnOptionOne?.text = question!!.answerOptions[0].answerText
        btnOptionTwo?.text = question!!.answerOptions[1].answerText
        btnOptionThree?.text = question!!.answerOptions[2].answerText
        btnOptionFour?.text = question!!.answerOptions[3].answerText
        btnSubmitButton?.isEnabled = false
    }

    private fun setButtonSelected(view: Button) {
        view.background =
            ContextCompat.getDrawable(this, R.drawable.default_button_bg_selected)
        view.typeface = Typeface.DEFAULT_BOLD
    }

    private fun setButtonDefault(view: Button) {
        view.background = ContextCompat.getDrawable(this, R.drawable.default_button_bg_normal)
        view.typeface = Typeface.DEFAULT
    }
}