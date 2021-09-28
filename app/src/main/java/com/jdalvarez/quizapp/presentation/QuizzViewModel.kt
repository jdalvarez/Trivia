package com.jdalvarez.quizapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizzViewModel(private val repo: Repository): ViewModel() {

    val navigateToFinishScreen = SingleLiveEvent<Boolean>()
    val questionLiveData = MutableLiveData<Question>()
    val LoadingLiveData = MutableLiveData<Boolean>()
    var scoreLiveData = MutableLiveData<Int>()

    private var correctAnswerCount = 0
    private var nextQuestion = 0
    private val questionList = mutableListOf<Question>()

    fun onViewCreated() {
        fetchQuestionsList()
        navigateToFinishScreen.value = false
    }

    fun checkAnswer(click:Boolean){
        if (click == questionLiveData.value?.answer){
            correctAnswerCount++
            scoreLiveData.value = correctAnswerCount
            loadNextQuestion()
        }else{
            loadNextQuestion()
        }
    }

    private fun fetchQuestionsList(){
        viewModelScope.launch(Dispatchers.IO) {
            val randomQuestions = repo.getRandomQuestions(QUESTIONS_NUMBER)
            withContext(Dispatchers.Main){
                randomQuestions?.let {
                    questionList.addAll(it)
                    loadNextQuestion()
                }
            }
        }
    }

    private fun loadNextQuestion(){
        if(nextQuestion < QUESTIONS_NUMBER) {
            questionLiveData.value = questionList[nextQuestion++]
        } else {
            navigateToFinishScreen.value = true
            //save score correctAnswerCount
        }
    }

    companion object {
        private const val QUESTIONS_NUMBER = 5
    }

}

class QuizzViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizzViewModel(repo) as T
    }
}