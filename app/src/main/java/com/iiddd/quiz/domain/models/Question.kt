package com.iiddd.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val questionText: String,
    val imagePrefix: String,
    val answerOptions: MutableList<Answer>
)
