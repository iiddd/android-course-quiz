package com.iiddd.quiz

data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val answerOptions: List<Answer>
)
