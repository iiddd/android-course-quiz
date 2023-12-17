package com.iiddd.quiz.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.iiddd.quiz.common.Constants.PREF_NAME
import com.iiddd.quiz.common.Constants.SCORE_PREF_KEY
import com.iiddd.quiz.common.Constants.USERNAME_PREF_DEFAULT_VALUE
import com.iiddd.quiz.common.Constants.USERNAME_PREF_KEY
import com.iiddd.quiz.domain.repository.UserDataRepository
import dagger.hilt.android.internal.Contexts.getApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepositoryImpl @Inject constructor(val context: Context) : UserDataRepository {

    private val pref: SharedPreferences =
        getApplication(context).getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    override fun storeUsername(username: String) {
        editor.putString(USERNAME_PREF_KEY, username)
        editor.commit()
    }

    override fun getUsername(): String {
        return pref.getString(USERNAME_PREF_KEY, USERNAME_PREF_DEFAULT_VALUE)!!
    }

    override fun storeScore(score: Int) {
        editor.putInt(SCORE_PREF_KEY, score)
        editor.commit()
    }

    override fun getScore(): Int {
        return pref.getInt(SCORE_PREF_KEY, 0)
    }

    override fun clearUserScore() {
        editor.putInt(SCORE_PREF_KEY, 0)
        editor.commit()
    }
}