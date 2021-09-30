package com.jdalvarez.quizapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdalvarez.quizapp.data.Play
import com.jdalvarez.quizapp.data.User
import com.jdalvarez.quizapp.repository.Repository
import com.jdalvarez.quizapp.util.QuizConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FinishScreenViewModel(private val repo: Repository) : ViewModel() {
    val userWinLose = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val userLiveData = MutableLiveData<User>()

    fun onViewCreated(email: String, score: Int) {
        getUser(email)
        userPlayResult(email, score)
    }

    private fun userPlayResult(email: String, finalScore: Int) {
        if (finalScore >= QuizConfig.QUESTIONS_NUMBER) {
            userWinLose.value = true
            savePlayResult(email, Play.WON.name)
        } else {
            userWinLose.value = false
            savePlayResult(email, Play.LOST.name)
        }

    }

    fun savePlayResult(email: String, played: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveDataUser(email, played)
            withContext(Dispatchers.Main) {
                loadingLiveData.value = false
            }
        }
    }

    private fun getUser(email: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.readUser(email)
            withContext(Dispatchers.Main) {
                user?.let {
                    userLiveData.value = it
                    loadingLiveData.value = false
                }
            }
        }
    }
}


class FinishScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FinishScreenViewModel(repo) as T
    }
}