package com.iiddd.quiz.ui.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuizUiState
import com.iiddd.quiz.ui.helper.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: GetQuestionUseCase = mockk()
    private val repository: UserDataRepository = mockk()
    private lateinit var mockedQuestionList: List<Question>
    private lateinit var viewModel: QuizViewModel

    @Before
    fun beforeTest() {
        mockedQuestionList = getQuestionListMock()
        every { useCase.invoke() } returns mockedQuestionList
        viewModel = QuizViewModel(useCase, repository)
    }

    @Test
    fun `When viewModel is initialized`() {
        val data = viewModel.questionStateFlow
        assertEquals(
            QuizUiState.Success(mockedQuestionList[0], 0).questionCounter,
            (data.value as QuizUiState.Success).questionCounter
        )
        checkQuestionStateIsPosted(data, 0)
        assertFalse(viewModel.quizResultStateFlow.value.isComplete)
    }

    @Test
    fun `When onSubmit is called and there are more questions next question is posted`() {
        runTest {
            viewModel.onSubmit()
            advanceTimeBy(2000)
            questionResultLiveDataIsSent()
            val data = viewModel.questionStateFlow
            checkQuestionStateIsPosted(data, 1)
        }
    }

    @Test
    fun `When onSubmit is called and there are no more questions`() {
        mockedQuestionList = getQuestionListMock().dropLast(1)
        every { useCase.invoke() } returns mockedQuestionList
        viewModel = QuizViewModel(useCase, repository)
        runTest {
            viewModel.onSubmit(0)
            advanceTimeBy(2000)
            assertTrue(viewModel.quizResultStateFlow.value.isComplete)
        }
    }

    private fun checkQuestionStateIsPosted(data: StateFlow<QuizUiState>, questionIndex: Int) {
        assertEquals(
            mockedQuestionList[questionIndex],
            (data.value as QuizUiState.Success).question
        )
    }

    private fun questionResultLiveDataIsSent() {
        assertEquals(
            99,
            (viewModel.questionResultStateFlow.value).selectedAnswerIndex
        )
        assertEquals(
            4,
            (viewModel.questionResultStateFlow.value).correctAnswerIndex
        )
    }

    private fun getQuestionListMock(): List<Question> {
        return listOf(
            Question(
                id = 0,
                questionText = "Test",
                imagePrefix = "nl",
                answerOptions = mutableListOf(
                    Answer(
                        answerText = "answerText5",
                        isCorrect = false,
                        index = 5
                    ),
                    Answer(
                        answerText = "answerText4",
                        isCorrect = true,
                        index = 4
                    )
                )
            ),
            Question(
                id = 1,
                questionText = "Test 2",
                imagePrefix = "be",
                answerOptions = mutableListOf(
                    Answer(
                        answerText = "answerText1",
                        isCorrect = true,
                        index = 1
                    ),
                    Answer(
                        answerText = "answerText2",
                        isCorrect = false,
                        index = 2
                    )
                )
            )
        )
    }
}