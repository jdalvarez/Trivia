package com.jdalvarez.quizapp.data

data class QuestionPool(val questions: List<Question>)

data class Question(
    val alcance: String,
    val questionText: String,
    val answer: Boolean,
    val rightAnswer: String
)

