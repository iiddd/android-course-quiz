package com.iiddd.quiz.ui.welcome

import com.iiddd.quiz.domain.repository.UserDataRepository
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class WelcomeViewModelTest {

    private val repository: UserDataRepository = mockk()
    private val viewModel = WelcomeViewModel(repository)

    @Test
    fun `When saveUserName is called`() {
        val username = "TestUser"
        justRun { repository.storeUsername(username) }

        viewModel.saveUsername(username)

        verify { repository.storeUsername(username) }
    }

    @Test
    fun `When getUserName is called`() {
        val response = "TestUser"
        every { repository.getUsername() } returns response

        val returned = viewModel.getUserName()

        verify { repository.getUsername() }
        assert(response == returned)
    }
}