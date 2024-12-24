package com.iiddd.quiz.ui.result

sealed class ResultUiState {

    class Success(
        val username: String,
        val score: Int,
        val total: Int
    ) : ResultUiState()
}