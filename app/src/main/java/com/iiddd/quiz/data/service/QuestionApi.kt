package com.iiddd.quiz.data.service

import com.iiddd.quiz.domain.models.Question

interface QuestionApi {

    fun getQuestionList(): List<Question>
}