package com.iiddd.quiz.ui.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.domain.repository.UserDataRepository
import com.iiddd.quiz.domain.usecase.GetQuestionUseCase
import com.iiddd.quiz.ui.entity.QuestionUiState
import com.iiddd.quiz.ui.helper.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
        mockedQuestionList = listOf(
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
        every { useCase.invoke() } returns mockedQuestionList
        viewModel = QuizViewModel(useCase, repository)
    }

    @Test
    fun `When viewModel is initialized`() {
        val data = viewModel.questionStateFlow
        assertEquals(QuestionUiState.Success(mockedQuestionList[0], 0), data.value)
        checkQuestionStateIsPosted(data, 0)
    }

    @Test
    fun `When submit is called and there are more questions next question is posted`() {
        runTest {
            viewModel.submit(99)
            advanceTimeBy(2000)
            questionResultLiveDataIsSent()
            val data = viewModel.questionStateFlow
            checkQuestionStateIsPosted(data, 1)
        }
    }

//    @Test
//    fun `When submit is called and there are no more questions`() {
//        runTest {
//            viewModel.submit(0)
//
//        }
//    }

    private fun checkQuestionStateIsPosted(data: StateFlow<QuestionUiState>, questionIndex: Int) {
        assertEquals(
            mockedQuestionList[questionIndex],
            (data.value as QuestionUiState.Success).question
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
}