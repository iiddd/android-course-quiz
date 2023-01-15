package com.iiddd.quiz

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val QUESTIONS_FILE_NAME = "res/raw/questions.json"
private const val COUNTRIES_FILE_NAME = "res/raw/countries.json"

object QuestionManager {

    fun getQuestionList(): List<Question> {
        val questionList = Json.decodeFromString<List<Question>>(getQuestionsString()!!)
        questionList.stream().forEach {
            val wrongAnswers = getRandomWrongAnswers(it)
            it.answerOptions.addAll(wrongAnswers)
            it.answerOptions.shuffle()
        }
        return questionList.asSequence().shuffled().take(10).toList()
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
}