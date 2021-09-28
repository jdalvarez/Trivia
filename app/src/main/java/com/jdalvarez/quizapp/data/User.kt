package com.jdalvarez.quizapp.data

enum class Play {
    NOT_PLAYED,
    WON,
    LOST
}
enum class Modalidad {
    PRESENCIAL,
    DISTANCIA
}

data class User(
    val firstName: String="",
    val lastName: String="",
    val dni:String="",
    val email: String="",
    val carrera:String="",
    val modalidad: Modalidad= Modalidad.DISTANCIA,
    val result: Play = Play.NOT_PLAYED
)


