package com.iiddd.quiz.ui.welcome

import androidx.lifecycle.ViewModel
import com.iiddd.quiz.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    fun saveUsername(username: String) {
        userDataRepository.storeUsername(username)
    }

    fun setIsDefault(isDefault: Boolean) {
        userDataRepository.setIsDefault(isDefault)
    }

    fun getUserName(): String {
        return userDataRepository.getUsername()
    }

    fun getIsDefault(): Boolean {
        return userDataRepository.getIsDefault()
    }
}