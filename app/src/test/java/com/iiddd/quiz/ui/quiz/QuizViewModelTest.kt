package com.iiddd.quiz.ui.quiz

import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class QuizViewModelTest {

    private val useCase: GetQuestionUseCase = mockk()
    private val repository: UserDataRepository = mockk()
    private val viewModel: QuizViewModel = QuizViewModel(useCase, repository)

}