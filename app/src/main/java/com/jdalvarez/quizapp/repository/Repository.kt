package com.jdalvarez.quizapp.repository

import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.data.User

interface Repository {
    fun getRandomQuestions(quantity: Int = 3): List<Question>?
    fun loadUniveristyDegrees(): List<String>

    //firebase
    suspend fun saveUser(
        firstName: String?,
        lastName: String?,
        dni: String?,
        email: String?,
        carrera: String?,
        modalidad: String?,
        played: String?
    )

    suspend fun readUser(email: String): User?
    suspend fun saveDataUser(email: String, played: String)
}