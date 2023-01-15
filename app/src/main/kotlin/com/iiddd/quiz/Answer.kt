package com.iiddd.quiz

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val answerText: String, val isCorrect: Boolean
)