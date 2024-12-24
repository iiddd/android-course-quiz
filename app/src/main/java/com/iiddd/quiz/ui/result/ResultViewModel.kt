package com.iiddd.quiz.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iiddd.quiz.common.Constants
import com.iiddd.quiz.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _resultUiStateFlow = MutableStateFlow<ResultUiState>(
        ResultUiState.Success(
            username = getUserName(),
            score = getUserScore(),
            total = Constants.QUESTION_COUNT
        )
    )
    val resultUiState: StateFlow<ResultUiState> = _resultUiStateFlow

    init {
        updateUiState()
    }

    private fun updateUiState() {
        viewModelScope.launch(dispatcher) {
            _resultUiStateFlow.emit(
                ResultUiState.Success(
                    username = getUserName(),
                    score = getUserScore(),
                    total = Constants.QUESTION_COUNT
                )
            )
        }
    }

    fun getUserName(): String {
        return userDataRepository.getUsername()
    }

    fun getUserScore(): Int {
        return userDataRepository.getScore()
    }

    fun clearUserScore() {
        userDataRepository.clearUserScore()
    }
}