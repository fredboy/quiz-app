package ru.fredboy.quizapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizzesDto(
    val quizzes: List<QuizDto>,
    val timestamp: Long,
)
