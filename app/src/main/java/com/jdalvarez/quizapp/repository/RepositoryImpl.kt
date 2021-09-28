package com.jdalvarez.quizapp.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdalvarez.quizapp.data.*
import java.lang.reflect.Type

class RepositoryImpl(
    private val fileDataSource: FileDataSource,
    private val remoteDataSource: RemoteFileDataSource
) : Repository {

    override fun getRandomQuestions(quantity: Int): List<Question> {
        val jsonString = fileDataSource.loadFileFromAssets(FILE_QUESTION)
        val result = if (jsonString != null) Gson().fromJson(
            jsonString,
            QuestionPool::class.java
        ) else QuestionPool(arrayListOf())

        return result.questions.shuffled().take(quantity)
    }

    override fun loadUniveristyDegrees(): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        val jsonString = fileDataSource.loadFileFromAssets(FILE_DEGREES)
        var result: List<String> =  if (jsonString != null) Gson().fromJson(jsonString, listType) else arrayListOf()
        return result.sorted()
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

    companion object {
        private const val FILE_QUESTION = "questions.json"
        private const val FILE_DEGREES = "degrees.json"
    }
}

