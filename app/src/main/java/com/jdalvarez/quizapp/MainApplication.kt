package com.jdalvarez.quizapp

import android.app.Application
import com.jdalvarez.quizapp.repository.FileDataSource
import com.jdalvarez.quizapp.repository.Repository
import com.jdalvarez.quizapp.repository.RepositoryImpl

class MainApplication: Application(){
    val fileDataSource by lazy{ FileDataSource(this)}
    val respository: Repository by lazy{ RepositoryImpl(fileDataSource)}
}