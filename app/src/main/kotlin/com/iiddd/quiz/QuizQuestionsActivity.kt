package com.iiddd.quiz

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.iiddd.quiz.Constants.CORRECT_ANSWERS
import com.iiddd.quiz.Constants.TOTAL_QUESTION
import com.iiddd.quiz.Constants.USER_NAME

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
    private var currentPosition = 1
    private val questionList = QuestionManager.getQuestionList()
    private val submitDelay: Long = 1500
    private var userName: String? = null
    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra(USER_NAME)
        initScreen()
        setQuestion()
        setupAnswerOne()
        setupAnswerTwo()
        setupAnswerThree()
        setupAnswerFour()
        onSubmit()
    }

    private fun onSubmit() {
        btnSubmitButton?.setOnClickListener {
            setButtonsNonClickable()
            if (question!!.answerOptions[selectedAnswer!!].isCorrect) {
                setButtonCorrect(getSelectedAnswerButton())
                correctAnswers++
            } else {
                setButtonIncorrect(getSelectedAnswerButton())
                showCorrectAnswer()
            }
            if (questionList.size > currentPosition) {
                setNextQuestion()
            } else {
                navigateToResultScreen()
            }
        }
    }

    private fun navigateToResultScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(USER_NAME, userName)
            intent.putExtra(TOTAL_QUESTION, questionList.size)
            intent.putExtra(CORRECT_ANSWERS, correctAnswers)
            startActivity(intent)
            finish()
        }, submitDelay)
    }

    private fun setNextQuestion() {
        Handler(Looper.getMainLooper()).postDelayed({
            currentPosition++
            setQuestion()
        }, submitDelay)
    }

    private fun initScreen() {
        setContentView(R.layout.activity_quiz_questions)
        progressBar = findViewById(R.id.pb_progress_bar)
        progressText = findViewById(R.id.tv_progress_text)
        questionText = findViewById(R.id.tv_question_text)
        flagImage = findViewById(R.id.iv_flag)
        btnOptionOne = findViewById(R.id.btn_answer1)
        btnOptionTwo = findViewById(R.id.btn_answer2)
        btnOptionThree = findViewById(R.id.btn_answer3)
        btnOptionFour = findViewById(R.id.btn_answer4)
        btnSubmitButton = findViewById(R.id.btn_question_submit)
    }

    private fun setupAnswerOne() {
        btnOptionOne?.setOnClickListener {
            setAllAnswersUnselected()
            setButtonSelected(btnOptionOne)
            selectedAnswer = 0
            enableSubmitButton()
        }
    }

    private fun setupAnswerTwo() {
        btnOptionTwo?.setOnClickListener {
            setAllAnswersUnselected()
            setButtonSelected(btnOptionTwo)
            selectedAnswer = 1
            enableSubmitButton()
        }
    }

    private fun setupAnswerThree() {
        btnOptionThree?.setOnClickListener {
            setAllAnswersUnselected()
            setButtonSelected(btnOptionThree)
            selectedAnswer = 2
            enableSubmitButton()
        }
    }

    private fun setupAnswerFour() {
        btnOptionFour?.setOnClickListener {
            setAllAnswersUnselected()
            setButtonSelected(btnOptionFour)
            selectedAnswer = 3
            enableSubmitButton()
        }
    }

    private fun setQuestion() {
        Log.i("QuestionQuiz", "Question list size is ${questionList.size}")

        for (i in questionList) {
            Log.e("Questions", i.questionText)
        }

        question = questionList[currentPosition - 1]
        progressBar?.progress = currentPosition
        flagImage?.setImageResource(getImageId(question!!.imagePrefix))
        progressText?.text = "$currentPosition / ${progressBar?.max}"
        questionText?.text = question!!.questionText
        btnOptionOne?.text = question!!.answerOptions[0].answerText
        btnOptionTwo?.text = question!!.answerOptions[1].answerText
        btnOptionThree?.text = question!!.answerOptions[2].answerText
        btnOptionFour?.text = question!!.answerOptions[3].answerText
        btnSubmitButton?.isEnabled = false
        setAllAnswersUnselected()
        setAnswerButtonClickable()
    }

    private fun enableSubmitButton() {
        btnSubmitButton?.isEnabled = true
    }

    private fun setAllAnswersUnselected() {
        setButtonDefault(btnOptionOne)
        setButtonDefault(btnOptionTwo)
        setButtonDefault(btnOptionThree)
        setButtonDefault(btnOptionFour)
    }

    private fun setButtonsNonClickable() {
        btnOptionOne?.isClickable = false
        btnOptionTwo?.isClickable = false
        btnOptionThree?.isClickable = false
        btnOptionFour?.isClickable = false
        btnSubmitButton?.isClickable = false
    }

    private fun setAnswerButtonClickable() {
        btnOptionOne?.isClickable = true
        btnOptionTwo?.isClickable = true
        btnOptionThree?.isClickable = true
        btnOptionFour?.isClickable = true
        btnSubmitButton?.isClickable = true
    }

    private fun setButtonSelected(view: Button?) {
        view?.background =
            ContextCompat.getDrawable(this, R.drawable.default_button_bg_selected)
        view?.typeface = Typeface.DEFAULT_BOLD
    }

    private fun setButtonDefault(view: Button?) {
        view?.background = ContextCompat.getDrawable(this, R.drawable.default_button_bg_normal)
        view?.typeface = Typeface.DEFAULT
    }

    private fun setButtonCorrect(view: Button?) {
        view?.background = ContextCompat.getDrawable(this, R.drawable.default_button_bg_green)
    }

    private fun setButtonIncorrect(view: Button?) {
        view?.background = ContextCompat.getDrawable(this, R.drawable.default_button_bg_red)
    }

    private fun showCorrectAnswer() {
        for (i in 0 until question!!.answerOptions.size) {
            if (question!!.answerOptions[i].isCorrect) {
                setButtonCorrect(getAnswerButtonByIndex(i) as Button)
                break
            }
        }
    }

    private fun getSelectedAnswerButton(): Button? {
        return getAnswerButtonByIndex(selectedAnswer!!)
    }

    private fun getImageId(name: String): Int {
        return resources.getIdentifier("flag_$name", "drawable", packageName)
    }

    private fun getAnswerButtonByIndex(index: Int): Button? {
        return when (index) {
            0 -> btnOptionOne
            1 -> btnOptionTwo
            2 -> btnOptionThree
            3 -> btnOptionFour
            else -> {
                null
            }
        }
    }
}