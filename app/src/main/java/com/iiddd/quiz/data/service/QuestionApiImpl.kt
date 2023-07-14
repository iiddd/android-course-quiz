package com.iiddd.quiz.data.service

import com.iiddd.quiz.common.Constants.QUESTION_COUNT
import com.iiddd.quiz.data.model.Countries
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import kotlinx.serialization.json.Json

class QuestionApiImpl : QuestionApi {

    override fun getQuestionList(): List<Question> {
        val questionList =
            Json.decodeFromString<List<Question>>(getQuestionsString()!!)
        questionList.stream().forEach {
            val wrongAnswers = getRandomWrongAnswers(it)
            it.answerOptions.addAll(wrongAnswers)
            it.answerOptions.shuffle()
            val answers = getIndexedQuestionAnswers(it)
            it.answerOptions.clear()
            it.answerOptions.addAll(answers)
        }
        return questionList.asSequence().shuffled().take(QUESTION_COUNT).toList()
    }

    private fun getCountryList(): Countries {
        return Json.decodeFromString<Countries>(getCountryListString()!!)
    }

    private fun getQuestionsString(): String? {
        return this.javaClass.classLoader?.getResource(QUESTIONS_FILE_NAME)?.readText()
    }

    private fun getCountryListString(): String? {
        return this.javaClass.classLoader?.getResource(COUNTRIES_FILE_NAME)?.readText()
    }

    private fun getRandomWrongAnswers(question: Question): List<Answer> {
        val correctAnswer = question.answerOptions[0].answerText
        val countries = getCountryList().countries
        countries.remove(correctAnswer)
        val wrongCountries = countries.asSequence().shuffled().take(3).toList()
        val wrongAnswers = mutableListOf<Answer>()
        for (wrongCountry in wrongCountries) {
            wrongAnswers.add(Answer(answerText = wrongCountry, isCorrect = false))
        }
        return wrongAnswers
    }

    private fun getIndexedQuestionAnswers(question: Question): List<Answer> {
        val answerOptions: MutableList<Answer> = mutableListOf()
        question.answerOptions.mapIndexed { index, answer ->
            answerOptions.add(
                Answer(
                    answerText = answer.answerText,
                    isCorrect = answer.isCorrect,
                    index = index
                )
            )
        }
        return answerOptions
    }

    companion object {
        private const val QUESTIONS_FILE_NAME = "res/raw/questions.json"
        private const val COUNTRIES_FILE_NAME = "res/raw/countries.json"
    }
}