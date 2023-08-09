package com.iiddd.quiz.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiddd.quiz.common.Constants.QUESTION_COUNT
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuestionResult
import com.iiddd.quiz.ui.entity.QuestionUiState
import com.iiddd.quiz.ui.entity.QuizResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    useCase: GetQuestionUseCase
) : ViewModel() {

    private val _questionUiStateLiveData = MutableLiveData<QuestionUiState>()
    val questionLiveData: LiveData<QuestionUiState> = _questionUiStateLiveData

    private val _quizResultLiveData = MutableLiveData<QuizResultState>()
    val quizResultLiveData: LiveData<QuizResultState> = _quizResultLiveData

    private val _questionResultLiveData = MutableLiveData<QuestionResult>()
    val questionResultLiveData: LiveData<QuestionResult> = _questionResultLiveData

    private val questionList: List<Question> = useCase.invoke()
    private var score: Int = 0
    private var counter: Int = 0

    init {
        postQuestionUiState()
    }

    private fun postQuestionUiState() {
        if (counter < QUESTION_COUNT) {
            _questionUiStateLiveData.postValue(
                QuestionUiState.Success(
                    questionCounter = counter,
                    question = questionList[counter]
                )
            )
        } else getQuizResultState()
    }

    private fun getCorrectAnswerIndex(): Int {
        return questionList[counter].answerOptions.first { i -> i.isCorrect }.index
    }

    private fun incrementScore(selectedAnswerIndex: Int, correctAnswerIndex: Int) {
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
        }
    }

    private fun getQuizResultState() {
        _quizResultLiveData.postValue(
            QuizResultState()
        )
    }

    fun submit(selectedAnswerIndex: Int) {
        viewModelScope.launch {
            _questionResultLiveData.postValue(
                QuestionResult(
                    selectedAnswerIndex = selectedAnswerIndex,
                    correctAnswerIndex = getCorrectAnswerIndex()
                )
            )
            delay(1500L)
            incrementScore(
                selectedAnswerIndex = selectedAnswerIndex,
                correctAnswerIndex = getCorrectAnswerIndex()
            )
            counter++
            postQuestionUiState()
        }
    }
}