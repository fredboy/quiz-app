package ru.fredboy.quizapp.domain.model

data class QuizDetails(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val passingScore: Int,
    val questions: List<Question>,
)
