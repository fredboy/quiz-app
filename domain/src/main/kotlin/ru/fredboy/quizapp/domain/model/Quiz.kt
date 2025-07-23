package ru.fredboy.quizapp.domain.model

data class Quiz(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val passingScore: Int,
    val numberOfQuestions: Int,
)
