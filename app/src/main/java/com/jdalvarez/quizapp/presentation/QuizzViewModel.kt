package com.jdalvarez.quizapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jdalvarez.quizapp.repository.Repository

class QuizzViewModel(private val repo: Repository): ViewModel() {


}

class QuizzViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizzViewModel(repo) as T
    }
}