package com.jdalvarez.quizapp.repository

import android.content.Context
import com.google.gson.Gson
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.data.QuestionPool

class RepositoryImpl(private val fileDataSource: FileDataSource):Repository {

    override fun getRandomQuestions(quantity: Int): List<Question> {
        val jsonString = fileDataSource.loadFileFromAssets(FILE_QUESTION)
        val result = if (jsonString != null)Gson().fromJson(
            jsonString,
            QuestionPool::class.java
        )else QuestionPool(arrayListOf())

        return result.questions.shuffled().take(quantity)
    }

    companion object{
        private const val FILE_QUESTION = "questions.json"
    }
}

