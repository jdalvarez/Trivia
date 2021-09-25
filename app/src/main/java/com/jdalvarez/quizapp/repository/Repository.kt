package com.jdalvarez.quizapp.repository

import com.jdalvarez.quizapp.data.Question

interface Repository {
    fun getRandomQuestions(quantity: Int = 3): List<Question>
}