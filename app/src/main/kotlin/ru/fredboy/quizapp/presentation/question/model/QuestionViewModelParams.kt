package ru.fredboy.quizapp.presentation.question.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionViewModelParams(
    val quizId: Int,
)
