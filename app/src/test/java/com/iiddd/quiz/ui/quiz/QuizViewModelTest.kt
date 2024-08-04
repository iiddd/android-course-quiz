package com.iiddd.quiz.ui.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuizUiState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val useCase: GetQuestionUseCase = mockk()
    private val repository: UserDataRepository = mockk()
    private lateinit var mockedQuestionList: List<Question>
    private lateinit var viewModel: QuizViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(testDispatcher)
        mockedQuestionList = getQuestionListMock()
        every { useCase.invoke() } returns mockedQuestionList
        viewModel = QuizViewModel(
            dispatcher = testDispatcher,
            getQuestionUseCase = useCase,
            userDataRepository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When viewModel is initialized`() {
        val data = viewModel.questionStateFlow
        assertEquals(
            QuizUiState.Success(mockedQuestionList[0], 0).questionCounter,
            (data.value as QuizUiState.Success).questionCounter - 1
        )
        checkQuestionStateIsPosted(data, 0)
        assertFalse(viewModel.questionStateFlow.value is QuizUiState.Complete)
    }

    @Test
    fun `When onSubmit is called and there are more questions next question is posted`() = runTest {
        every { repository.storeScore(1) }.returns(Unit)
        viewModel.onSubmit(1)
        advanceUntilIdle()
        val data = viewModel.questionStateFlow
        checkQuestionStateIsPosted(data, 1)
    }

    @Test
    fun `When onSubmit is called and there are no more questions`() = runTest {
        mockedQuestionList = getQuestionListMock().dropLast(1)
        every { useCase.invoke() } returns mockedQuestionList
        viewModel = QuizViewModel(
            dispatcher = testDispatcher,
            getQuestionUseCase = useCase,
            userDataRepository = repository
        )
        viewModel.onSubmit(0)
        advanceUntilIdle()
        assertTrue(viewModel.questionStateFlow.value is QuizUiState.Complete)
    }

    private fun checkQuestionStateIsPosted(data: StateFlow<QuizUiState>, questionIndex: Int) {
        assertEquals(
            mockedQuestionList[questionIndex],
            (data.value as QuizUiState.Success).question
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