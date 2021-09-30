package com.jdalvarez.quizapp.util

object QuizConfig {
    const val SHOW_ANSWER_TICK_MS = 1000L
    const val SHOW_ANSWER_TICK_COUNT = 8
    const val SHOW_ANSWER_GO_TO_NEXT_QUESTION_MS = SHOW_ANSWER_TICK_MS * SHOW_ANSWER_TICK_COUNT
    const val SHOW_ANSWER_DELAY_MS = (SHOW_ANSWER_TICK_COUNT - 1) * SHOW_ANSWER_TICK_MS
    const val QUESTIONS_NUMBER = 2
}