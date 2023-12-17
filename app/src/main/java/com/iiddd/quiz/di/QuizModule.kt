package com.iiddd.quiz.di

import android.content.Context
import com.iiddd.quiz.data.repository.QuestionRepositoryImpl
import com.iiddd.quiz.data.repository.UserDataRepositoryImpl
import com.iiddd.quiz.data.service.QuestionApi
import com.iiddd.quiz.data.service.QuestionApiImpl
import com.iiddd.quiz.domain.repository.QuestionRepository
import com.iiddd.quiz.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    @Singleton
    fun providesUserDataRepo(@ApplicationContext context: Context): UserDataRepository =
        UserDataRepositoryImpl(context = context)

//    @Provides
//    @Singleton
//    fun providesQuestionApi(): QuestionApi = QuestionApiImpl()

    @Provides
    @Singleton
    fun providesQuestionRepo(): QuestionRepository = QuestionRepositoryImpl()
}
