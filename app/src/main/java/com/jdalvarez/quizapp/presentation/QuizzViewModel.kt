package com.jdalvarez.quizapp.presentation

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.repository.Repository
import com.jdalvarez.quizapp.util.QuizConfig.QUESTIONS_NUMBER
import com.jdalvarez.quizapp.util.QuizConfig.SHOW_ANSWER_TICK_COUNT
import com.jdalvarez.quizapp.util.QuizConfig.SHOW_ANSWER_TICK_MS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizzViewModel(private val repo: Repository) : ViewModel() {

    val navigateToFinishScreen = SingleLiveEvent<Boolean>()
    val questionLiveData = MutableLiveData<Question>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val questionResultLiveData = MutableLiveData<QuestionResult>()
    var scoreLiveData = MutableLiveData<Int>()

    private var correctAnswerCount = 0
    private var currentQuestion = -1
    private val questionList = mutableListOf<Question>()

    fun onViewCreated() {
        fetchQuestionsList()
        navigateToFinishScreen.value = false
        questionResultLiveData.value = QuestionResult.NONE
    }

    fun onUserAnswer(userAnswer: Boolean) {
        loadingLiveData.value = true
        startTimer(userAnswer)
    }

    private fun startTimer(userAnswer: Boolean) {
        val timer = object :
            CountDownTimer(SHOW_ANSWER_TICK_MS * SHOW_ANSWER_TICK_COUNT, SHOW_ANSWER_TICK_MS) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished < 8 * SHOW_ANSWER_TICK_MS) {
                    val questionResult = if (userAnswer == questionList[currentQuestion].answer) QuestionResult.RIGHT else QuestionResult.WRONG
                    questionResultLiveData.value = questionResult
                    if (questionResult == QuestionResult.RIGHT) {
                        correctAnswerCount++
                        scoreLiveData.value = correctAnswerCount
                    }
                }
            }

            override fun onFinish() {
                loadNextQuestion()
            }
        }
        timer.start()
    }

    private fun fetchQuestionsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val randomQuestions = repo.getRandomQuestions(QUESTIONS_NUMBER)
            withContext(Dispatchers.Main) {
                randomQuestions?.let {
                    questionList.addAll(it)
                    loadNextQuestion()
                }
            }
        }
    }

    private fun loadNextQuestion() {
        if (currentQuestion < QUESTIONS_NUMBER) {
            questionResultLiveData.value = QuestionResult.NONE
            questionLiveData.value = questionList[++currentQuestion]
            loadingLiveData.value = false
        } else {
            navigateToFinishScreen.value = true
            //save score correctAnswerCount
        }
    }
}

enum class QuestionResult { RIGHT, WRONG, NONE }

class QuizzViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizzViewModel(repo) as T
    }
}