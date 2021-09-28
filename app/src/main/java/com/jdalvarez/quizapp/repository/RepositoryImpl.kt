package com.jdalvarez.quizapp.repository

import android.content.Context
import com.google.gson.Gson
import com.jdalvarez.quizapp.data.Play
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.data.QuestionPool
import com.jdalvarez.quizapp.data.User

class RepositoryImpl(private val fileDataSource: FileDataSource, private val remoteDataSource: RemoteFileDataSource):Repository {

    override fun getRandomQuestions(quantity: Int): List<Question> {
        val jsonString = fileDataSource.loadFileFromAssets(FILE_QUESTION)
        val result = if (jsonString != null)Gson().fromJson(
            jsonString,
            QuestionPool::class.java
        )else QuestionPool(arrayListOf())

        return result.questions.shuffled().take(quantity)
    }

    override suspend fun saveUser(
        firstName: String,
        lastName: String,
        dni: String,
        email: String,
        carrera: String,
        modalidad: String,
        played: String
    ) {
        remoteDataSource.saveUser(firstName, lastName, dni, email, carrera, modalidad, played)
    }


    override suspend fun readUser(): User? {
        TODO("Not yet implemented")
    }

    companion object{
        private const val FILE_QUESTION = "questions.json"
    }
}

