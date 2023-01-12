package com.iiddd.quiz

object Constants {

    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val questionNl = Question(
            id = 1,
            questionText = "What country does this flag belong to?",
            image = R.drawable.flag_nl,
            answerOptions = arrayListOf(
                Answer(
                    answerText = "Germany",
                    isCorrect = false
                ),
                Answer(
                    answerText = "The Netherlands",
                    isCorrect = true
                ),
                Answer(
                    answerText = "France",
                    isCorrect = false
                ),
                Answer(
                    answerText = "Austria",
                    isCorrect = false
                )
            )
        )
        questionList.add(questionNl)
        return questionList
    }
}