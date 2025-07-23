package ru.fredboy.quizapp.presentation.quizdetails.model

import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus

data class QuizDetailsVo(
    val quizDetails: QuizDetails,
    val status: QuizStatus?,
)
