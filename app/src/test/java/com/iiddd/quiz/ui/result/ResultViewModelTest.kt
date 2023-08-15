package com.iiddd.quiz.ui.result

import com.iiddd.quiz.domain.repository.UserDataRepository
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ResultViewModelTest {

    private val repository: UserDataRepository = mockk()
    private val viewModel = ResultViewModel(repository)

    @Test
    fun `When getUserName is called`() {
        val response = "TestUser"
        every { repository.getUsername() } returns response

        val returned = viewModel.getUserName()

        verify { repository.getUsername() }
        assert(response == returned)
    }

    @Test
    fun `When getUserScore is called`() {
        val response = 10
        every { repository.getScore() } returns response

        val returned = viewModel.getUserScore()

        verify { repository.getScore() }
        assert(response == returned)

    }

    @Test
    fun `When clearUserScore is called`() {
        justRun { repository.clearUserScore() }

        viewModel.clearUserScore()

        verify { repository.clearUserScore() }
    }
}