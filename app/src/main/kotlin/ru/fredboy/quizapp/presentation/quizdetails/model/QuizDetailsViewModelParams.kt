package ru.fredboy.quizapp.presentation.quizdetails.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizDetailsViewModelParams(
    val quizId: Int,
)
