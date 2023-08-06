package com.iiddd.quiz.ui.quiz

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.R
import com.iiddd.quiz.databinding.FragmentQuestionsBinding
import com.iiddd.quiz.ui.entity.QuestionResult
import com.iiddd.quiz.ui.entity.QuestionUiState
import com.iiddd.quiz.ui.entity.QuizResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionsBinding
    private val viewModel by viewModels<QuizViewModel> { QuizViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            btnAnswer1.setOnClickListener{
                openResultScreen(QuizResultState(username = "Test", score = 10))
            }
        }
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObservers() {
        viewModel.questionLiveData.observe(viewLifecycleOwner, ::updateQuestion)
        viewModel.questionResultLiveData.observe(viewLifecycleOwner, ::updateQuestionResult)
        viewModel.quizResultLiveData.observe(viewLifecycleOwner, ::openResultScreen)
    }

    private fun updateQuestion(questionUiState: QuestionUiState) {
        when (questionUiState) {
            is QuestionUiState.Success -> {
                with(binding) {
                    progressBar.progress = questionUiState.questionCounter
                    progressBarText.text =
                        getString(
                            R.string.progress,
                            questionUiState.questionCounter + 1,
                            progressBar.max
                        )
                    flagImage.setImageResource(getImageId(questionUiState.question.imagePrefix))
                    questionText.text = questionUiState.question.questionText
                    btnAnswer1.text = questionUiState.question.answerOptions[0].answerText
                    btnAnswer2.text = questionUiState.question.answerOptions[1].answerText
                    btnAnswer3.text = questionUiState.question.answerOptions[2].answerText
                    btnAnswer4.text = questionUiState.question.answerOptions[3].answerText
                    submitButton.isEnabled = false
                    setAllAnswersUnselected()
                    setAnswerButtonClickable()
                }
            }
        }
    }

    private fun updateQuestionResult(questionResult: QuestionResult) {
        setButtonsNonClickable()
        with(questionResult) {
            setButtonCorrect(correctAnswerIndex)
            if (correctAnswerIndex != selectedAnswerIndex) {
                setButtonIncorrect(selectedAnswerIndex)
            }
        }
    }

    private fun openResultScreen(quizResult: QuizResultState) {
//        startActivity(setUpIntent(QuestionResult::class.java).apply {
//            putExtra(Constants.USER_NAME, quizResult.username)
//            putExtra(Constants.CORRECT_ANSWERS, quizResult.score)
//        })
//        finish()
        findNavController().navigate(QuestionsFragmentDirections.goToResults())
    }

    private fun setButtonCorrect(index: Int) {
        getAnswerButtonByIndex(index)?.setBackgroundResource(R.drawable.default_button_bg_green)
    }

    private fun setButtonIncorrect(index: Int) {
        getAnswerButtonByIndex(index)?.setBackgroundResource(R.drawable.default_button_bg_red)
    }

    private fun setAllAnswersUnselected() {
        with(binding) {
            setButtonDefault(btnAnswer1)
            setButtonDefault(btnAnswer2)
            setButtonDefault(btnAnswer3)
            setButtonDefault(btnAnswer4)
        }
    }

    private fun setButtonsNonClickable() {
        with(binding) {
            btnAnswer1.isClickable = false
            btnAnswer2.isClickable = false
            btnAnswer3.isClickable = false
            btnAnswer4.isClickable = false
            submitButton.isClickable = false
        }
    }

    private fun setAnswerButtonClickable() {
        with(binding) {
            btnAnswer1.isClickable = true
            btnAnswer2.isClickable = true
            btnAnswer3.isClickable = true
            btnAnswer4.isClickable = true
            submitButton.isClickable = true
        }
    }

    private fun setButtonDefault(view: Button?) {
        view?.setBackgroundResource(R.drawable.default_button_bg_normal)
        view?.typeface = Typeface.DEFAULT
    }

    private fun getAnswerButtonByIndex(index: Int): Button? {
        with(binding) {
            return when (index) {
                0 -> btnAnswer1
                1 -> btnAnswer2
                2 -> btnAnswer3
                3 -> btnAnswer4
                else -> {
                    null
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun getImageId(name: String): Int {
        return resources.getIdentifier("flag_$name", "drawable", context?.packageName)
    }
}