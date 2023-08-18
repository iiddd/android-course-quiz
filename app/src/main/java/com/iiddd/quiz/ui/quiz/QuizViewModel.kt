package com.iiddd.quiz.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuestionResult
import com.iiddd.quiz.ui.entity.QuestionUiState
import com.iiddd.quiz.ui.entity.QuizResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    useCase: GetQuestionUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val questionList: List<Question> = useCase.invoke()
    private var counter: Int = 0
    private var score: Int = 0

    private val _questionUiStateLiveData =
        MutableStateFlow<QuestionUiState>(QuestionUiState.Success(questionList[0], counter))
    val questionStateFlow: StateFlow<QuestionUiState> = _questionUiStateLiveData

    private val _quizResultLiveData = MutableStateFlow(QuizResultState(false))
    val quizResultStateFlow: StateFlow<QuizResultState> = _quizResultLiveData

    private val _questionResultLiveData = MutableStateFlow(QuestionResult(-1, -1))
    val questionResultStateFlow: StateFlow<QuestionResult> = _questionResultLiveData

    init {
        postQuestionUiState()
    }

    fun submit(selectedAnswerIndex: Int) {
        viewModelScope.launch {
            _questionResultLiveData.emit(
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

    private fun postQuestionUiState() {
        viewModelScope.launch {
            if (counter < questionList.size) {
                _questionUiStateLiveData.emit(
                    QuestionUiState.Success(
                        questionCounter = counter,
                        question = questionList[counter]
                    )
                )
            } else getQuizResultState()
        }
    }

    private fun getCorrectAnswerIndex(): Int {
        return questionList[counter].answerOptions.first { i -> i.isCorrect }.index
    }

    private fun incrementScore(selectedAnswerIndex: Int, correctAnswerIndex: Int) {
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            userDataRepository.storeScore(score)
        }
    }

    private fun getQuizResultState() {
        viewModelScope.launch {
            _quizResultLiveData.emit(
                QuizResultState(true)
            )
        }
    }
}