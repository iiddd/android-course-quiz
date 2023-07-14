package com.iiddd.quiz.domain.usecase

import com.iiddd.quiz.domain.repository.QuestionRepository

class GetQuestionUseCase(private val repository: QuestionRepository) {

    operator fun invoke() = repository.getQuestionList()
}