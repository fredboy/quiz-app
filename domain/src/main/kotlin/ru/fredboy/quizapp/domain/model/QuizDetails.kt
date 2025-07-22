package ru.fredboy.quizapp.domain.model

data class QuizDetails(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val passingScore: Int,
    val questions: List<Question>,
)
