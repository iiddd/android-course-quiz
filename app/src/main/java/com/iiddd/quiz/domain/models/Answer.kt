package com.iiddd.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val answerText: String,
    val isCorrect: Boolean,
    val index: Int = -1
)