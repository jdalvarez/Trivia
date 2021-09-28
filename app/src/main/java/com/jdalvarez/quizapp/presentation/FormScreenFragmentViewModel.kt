package com.jdalvarez.quizapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdalvarez.quizapp.data.User

import com.jdalvarez.quizapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormScreenFragmentViewModel(private val repo: Repository): ViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()

    fun saveUser(user: User){
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO){
            repo.saveUser(user.firstName,user.lastName,user.dni, user.email, user.carrera, user.modalidad.name,user.result.name)
            withContext(Dispatchers.Main){
                loadingLiveData.value=false
            }
        }
    }
}

class FormScreenFragmentViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormScreenFragmentViewModel(repo) as T
    }
}