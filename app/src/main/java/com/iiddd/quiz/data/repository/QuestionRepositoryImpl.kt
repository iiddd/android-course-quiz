package com.iiddd.quiz.data.repository

import com.iiddd.quiz.data.service.QuestionApi
import com.iiddd.quiz.data.service.QuestionApiImpl
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
//    private val questionApi: QuestionApi
) : QuestionRepository {

    override fun getQuestionList(): List<Question> {
        println("test")
        println("test")
        val questionApi = QuestionApiImpl()
        return questionApi.getQuestionList()
    }
}