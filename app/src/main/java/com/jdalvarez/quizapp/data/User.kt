package com.jdalvarez.quizapp.data

enum class Play {
    NOT_PLAYED,
    WON,
    LOST
}

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val result: Play = Play.NOT_PLAYED
)


