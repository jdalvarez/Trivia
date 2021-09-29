package com.jdalvarez.quizapp.data

import java.lang.IllegalArgumentException

enum class Play {
    NOT_PLAYED,
    WON,
    LOST;

    companion object {
        fun parse(str: String?): Play {
            try {
                return Play.valueOf(str ?: "")
            } catch (iae: IllegalArgumentException) {
                return NOT_PLAYED
            }
        }
    }
}

enum class Modalidad {
    PRESENCIAL,
    DISTANCIA,
    UNKNOWN;

    companion object {
        fun parse(str: String?): Modalidad {
            try {
                return valueOf(str ?: "")
            } catch (iae: IllegalArgumentException) {
                return UNKNOWN
            }
        }
    }
}

data class User(
    val firstName: String? = "",
    val lastName: String? = "",
    val dni: String? = "",
    val email: String? = "",
    val carrera: String? = "",
    val modalidad: Modalidad? = Modalidad.DISTANCIA,
    val result: Play? = Play.NOT_PLAYED
)


