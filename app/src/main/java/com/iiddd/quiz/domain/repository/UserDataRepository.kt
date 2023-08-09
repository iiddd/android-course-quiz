package com.iiddd.quiz.domain.repository

interface UserDataRepository {

    fun storeUsername(username: String)
    fun getUsername(): String
    fun storeScore(score: Int)
    fun getScore(): Int
    fun clearUserData()
}