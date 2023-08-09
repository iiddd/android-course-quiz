package com.iiddd.quiz.data.repository

import com.iiddd.quiz.data.service.QuestionApi
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.QuestionRepository

class QuestionRepositoryImpl(private val questionApi: QuestionApi) : QuestionRepository {

    override fun getQuestionList(): List<Question> {
        return questionApi.getQuestionList()
    }
}