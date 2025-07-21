package ru.fredboy.quizapp.domain.model

data class Question(
    val id: Int,
    val text: String,
    val answers: List<Answer>,
    val correctAnswerId: Int,
)
