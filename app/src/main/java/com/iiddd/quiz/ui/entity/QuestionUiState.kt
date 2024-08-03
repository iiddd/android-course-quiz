package com.iiddd.quiz.ui.entity

import com.iiddd.quiz.domain.models.Question

sealed class QuestionUiState {

    class Success(
        val question: Question,
        val questionCounter: Int,
    ) : QuestionUiState()
}