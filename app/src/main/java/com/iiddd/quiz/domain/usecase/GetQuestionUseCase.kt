package com.iiddd.quiz.domain.usecase

import com.iiddd.quiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    operator fun invoke() = repository.getQuestionList()
}