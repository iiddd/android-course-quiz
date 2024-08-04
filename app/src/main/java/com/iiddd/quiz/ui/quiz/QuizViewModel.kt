package com.iiddd.quiz.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuizUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    getQuestionUseCase: GetQuestionUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val questionList: List<Question> = getQuestionUseCase.invoke()
    private var counter: Int = 1
    private var score: Int = 0

    private val _questionUiStateFlow =
        MutableStateFlow<QuizUiState>(QuizUiState.Success(questionList[0], counter))
    val questionStateFlow: StateFlow<QuizUiState> = _questionUiStateFlow

    init {
        updateUiState()
    }

    fun onSubmit(selectedAnswerIndex: Int) {
        viewModelScope.launch(dispatcher) {
            if (isCorrectAnswer(selectedAnswerIndex)) {
                incrementScore()
            }
            delay(1500L)
            counter++
            updateUiState()
        }
    }

    private fun updateUiState() {
        viewModelScope.launch(dispatcher) {
            if (counter - 1 < questionList.size) {
                _questionUiStateFlow.emit(
                    QuizUiState.Success(
                        questionCounter = counter,
                        question = questionList[counter - 1]
                    )
                )
            } else onComplete()
        }
    }

    private fun isCorrectAnswer(selectedAnswerIndex: Int): Boolean {
        return questionList[counter - 1].answerOptions[selectedAnswerIndex].isCorrect
    }

    private fun incrementScore() {
        score++
        userDataRepository.storeScore(score)
    }

    private fun onComplete() {
        viewModelScope.launch {
            _questionUiStateFlow.emit(
                QuizUiState.Complete
            )
        }
    }
}