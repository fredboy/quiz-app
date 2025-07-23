package ru.fredboy.quizapp.presentation.quizlist.model

import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus

data class QuizVo(
    val quiz: Quiz,
    val status: QuizStatus?,
)
