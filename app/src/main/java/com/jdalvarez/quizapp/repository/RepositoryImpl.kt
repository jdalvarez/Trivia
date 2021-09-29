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
        val result: List<String> =
            if (jsonString != null) Gson().fromJson(jsonString, listType) else arrayListOf()
        return result.sorted()
    }

    override suspend fun saveUser(
        firstName: String?,
        lastName: String?,
        dni: String?,
        email: String?,
        carrera: String?,
        modalidad: String?,
        played: String?
    ) {
        remoteDataSource.saveUser(firstName, lastName, dni, email, carrera, modalidad, played)
    }


    override suspend fun readUser(email: String): User? {
        var result: User? = null
        remoteDataSource.getUser(email)?.let {
            if (it.exists()) {
                val firstName = (it.getString("nombre"))
                val lastName = (it.getString("apellido"))
                val dni = (it.getString("dni"))
                val emails = (it.getString("email"))
                val carrera = (it.getString("carrera"))
                val modalidad = (it.getString("modalidad"))
                val jugo = it.getString("jugo")
                result = User(
                    firstName,
                    lastName,
                    dni,
                    emails,
                    carrera,
                    Modalidad.parse(modalidad),
                    Play.parse(jugo)
                )
            }
        }
        return result
    }

    override suspend fun saveDataUser(email: String, played: String) {
        remoteDataSource.savePlayResult(email, played)
    }

    companion object {
        private const val FILE_QUESTION = "questions.json"
        private const val FILE_DEGREES = "degrees.json"
    }
}

