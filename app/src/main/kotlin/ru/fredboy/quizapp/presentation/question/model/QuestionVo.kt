package ru.fredboy.quizapp.presentation.question.model

import ru.fredboy.quizapp.domain.model.Question

data class QuestionVo(
    val question: Question,
    val current: Int,
    val total: Int,
    val selectedAnswerId: Int?,
    val passingScore: Int,
)
