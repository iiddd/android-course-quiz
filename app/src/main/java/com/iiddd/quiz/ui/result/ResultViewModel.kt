package com.iiddd.quiz.ui.result

import androidx.lifecycle.ViewModel
import com.iiddd.quiz.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    fun getUserName(): String {
        return userDataRepository.getUsername()
    }

    fun getUserScore(): Int {
        return userDataRepository.getScore()
    }

    fun clearUserData() {
        userDataRepository.clearUserData()
    }
}