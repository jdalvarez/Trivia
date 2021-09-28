package com.jdalvarez.quizapp.data

data class QuestionPool(val questions: List<Question>)

data class Question(
    val alcance: String,
    val questionText: String,
    val answer: Boolean,
    val rightAnswerId: String
)

data class Answer(val answerText: String, val correctAnswer: String)

