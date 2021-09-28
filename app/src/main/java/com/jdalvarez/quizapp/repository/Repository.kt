package com.jdalvarez.quizapp.repository

import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.data.User

interface Repository {
    fun getRandomQuestions(quantity: Int = 3): List<Question>?

    //firebase
    suspend fun saveUser(firstName: String, lastName: String, dni: String, email: String, carrera: String, modalidad:String, played:String)
    suspend fun readUser(): User?
}