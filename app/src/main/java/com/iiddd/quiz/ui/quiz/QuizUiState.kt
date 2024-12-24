package com.iiddd.quiz.ui.quiz

import com.iiddd.quiz.domain.models.Question

sealed class QuizUiState {

    class Success(
        val question: Question,
        val questionCounter: Int,
    ) : QuizUiState()

    data object Complete : QuizUiState()
}