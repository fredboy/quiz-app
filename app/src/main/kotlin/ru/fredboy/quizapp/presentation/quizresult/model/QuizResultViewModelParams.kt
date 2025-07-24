package ru.fredboy.quizapp.presentation.quizresult.model

import kotlinx.serialization.Serializable
import ru.fredboy.quizapp.domain.model.QuizStatus

@Serializable
data class QuizResultViewModelParams(
    val quizId: Int,
    val title: String,
    val result: QuizStatus,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val passingScore: Int,
)
