package ru.fredboy.quizapp.domain.model

data class Quiz(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val status: QuizStatus,
    val passingScore: Int,
    val numberOfQuestions: Int,
)
