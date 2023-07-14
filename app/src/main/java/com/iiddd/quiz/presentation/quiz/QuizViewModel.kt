package com.iiddd.quiz.presentation.quiz

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iiddd.quiz.data.repository.QuestionRepositoryImpl
import com.iiddd.quiz.data.service.QuestionApiImpl
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.presentation.entity.QuestionResult
import com.iiddd.quiz.presentation.entity.QuestionUiState
import com.iiddd.quiz.presentation.entity.QuizResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizViewModel(val useCase: GetQuestionUseCase) : ViewModel() {

    private val _questionUiStateLiveData = MutableLiveData<QuestionUiState>()
    val questionLiveData: LiveData<QuestionUiState> = _questionUiStateLiveData

    private val _quizResultLiveData = MutableLiveData<QuizResultState>()
    val quizResultLiveData: LiveData<QuizResultState> = _quizResultLiveData

    private val _questionResultLiveData = MutableLiveData<QuestionResult>()
    val questionResultLiveData: LiveData<QuestionResult> = _questionResultLiveData

    private val questionList: List<Question> = useCase()
    private var score: Int = 0
    private var counter: Int = 0
    private lateinit var username: String

    init {
        postQuestionUiState()
    }

    private fun postQuestionUiState() {
        if (counter < 10) {
            _questionUiStateLiveData.postValue(
                QuestionUiState.Success(
                    questionCounter = counter,
                    question = questionList[counter]
                )
            )
            counter++
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
            QuizResultState(
                username = username,
                score = score
            )
        )
    }

    fun setUsername(username: String) {
        this.username = username
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
            postQuestionUiState()
            incrementScore(
                selectedAnswerIndex = selectedAnswerIndex,
                correctAnswerIndex = getCorrectAnswerIndex()
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                QuizViewModel(
                    useCase = GetQuestionUseCase(QuestionRepositoryImpl(QuestionApiImpl()))
                )
            }
        }
    }
}