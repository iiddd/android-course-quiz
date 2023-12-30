package com.iiddd.quiz.ui.quiz

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.R
import com.iiddd.quiz.common.Constants
import com.iiddd.quiz.databinding.FragmentQuestionsBinding
import com.iiddd.quiz.ui.entity.QuestionResult
import com.iiddd.quiz.ui.entity.QuestionUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionsBinding
    private val viewModel: QuizViewModel by viewModels()
    private var selectedAnswer: Int = -1
    private var isSubmitted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAnswerButtonListeners()
        setSubmit()
        initObservers()
        setFlagImageDims()
    }

    private fun setFlagImageDims() {
        if (Resources.getSystem().displayMetrics.heightPixels < 2000) {
            binding.flagImage.maxHeight = 460
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedAnswer", selectedAnswer)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            selectedAnswer = savedInstanceState.getInt("selectedAnswer")
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.questionStateFlow.collect {
                        updateQuestion(it)
                    }
                }
                launch {
                    viewModel.questionResultStateFlow.collect {
                        updateQuestionResult(it)
                    }
                }
                launch {
                    viewModel.quizResultStateFlow.collect {
                        if (it.isComplete) {
                            navigateToResultScreen()
                        }
                    }
                }
            }
        }
    }

    private fun updateQuestion(questionUiState: QuestionUiState) {
        when (questionUiState) {
            is QuestionUiState.Success -> {
                with(binding) {
                    progressBar.max = Constants.QUESTION_COUNT
                    progressBar.progress = questionUiState.questionCounter + 1
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
                    if (selectedAnswer == -1) {
                        setAllAnswersUnselected()
                    }  else {
                        enableSubmitButton()
                    }
                    setAnswerButtonClickable()
                }
            }
        }
    }

    private fun updateQuestionResult(questionResult: QuestionResult) {
        if (isSubmitted) {
            setButtonsNonClickable()
            with(questionResult) {
                setButtonCorrect(correctAnswerIndex)
                if (correctAnswerIndex != selectedAnswerIndex) {
                    setButtonIncorrect(selectedAnswerIndex)
                }
            }
            isSubmitted = false
        }
    }

    private fun navigateToResultScreen() {
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

    private fun setButtonSelected(view: Button?) {
        view?.setBackgroundResource(R.drawable.default_button_bg_selected)
        view?.typeface = Typeface.DEFAULT_BOLD
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

    private fun setAnswerButtonListeners() {
        setupAnswerOne()
        setupAnswerTwo()
        setupAnswerThree()
        setupAnswerFour()
    }

    private fun setSubmit() {
        binding.submitButton.setOnClickListener {
            isSubmitted = true
            viewModel.submit(selectedAnswer)
            selectedAnswer = -1
        }
    }

    private fun setupAnswerOne() {
        with(binding) {
            btnAnswer1.setOnClickListener {
                setAllAnswersUnselected()
                setButtonSelected(btnAnswer1)
                selectedAnswer = 0
                enableSubmitButton()
            }
        }
    }

    private fun setupAnswerTwo() {
        with(binding) {
            binding.btnAnswer2.setOnClickListener {
                setAllAnswersUnselected()
                setButtonSelected(btnAnswer2)
                selectedAnswer = 1
                enableSubmitButton()
            }
        }
    }

    private fun setupAnswerThree() {
        with(binding) {
            btnAnswer3.setOnClickListener {
                setAllAnswersUnselected()
                setButtonSelected(btnAnswer3)
                selectedAnswer = 2
                enableSubmitButton()
            }
        }
    }

    private fun setupAnswerFour() {
        with(binding) {
            btnAnswer4.setOnClickListener {
                setAllAnswersUnselected()
                setButtonSelected(btnAnswer4)
                selectedAnswer = 3
                enableSubmitButton()
            }
        }
    }

    private fun enableSubmitButton() {
        binding.submitButton.isEnabled = true
    }

    @SuppressLint("DiscouragedApi")
    private fun getImageId(name: String): Int {
        return resources.getIdentifier("flag_$name", "drawable", context?.packageName)
    }
}