package com.jdalvarez.quizapp.data

data class QuestionPool(val questions: List<Question>)

data class Question(
    val alcance: String,
    val questionText: String,
    //val answer: List<Answer>,
    val rightAnswerId: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String
)

//data class Answer(val answerText: String, val id: Int)

