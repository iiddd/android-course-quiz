package com.iiddd.quiz.domain.repository

import com.iiddd.quiz.domain.models.Question

interface QuestionRepository {

    fun getQuestionList(): List<Question>
}