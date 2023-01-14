package com.iiddd.quiz

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTION: String = "total_name"
    const val CORRECT_ANSWERS: String = "correct_answers"

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
        val questionFr = Question(
            id = 2,
            questionText = "What country does this flag belong to?",
            image = R.drawable.flag_fr,
            answerOptions = arrayListOf(
                Answer(
                    answerText = "Germany",
                    isCorrect = false
                ),
                Answer(
                    answerText = "The Netherlands",
                    isCorrect = false
                ),
                Answer(
                    answerText = "France",
                    isCorrect = true
                ),
                Answer(
                    answerText = "Austria",
                    isCorrect = false
                )
            )
        )
        questionList.add(questionNl)
        questionList.add(questionFr)
        return questionList
    }
}