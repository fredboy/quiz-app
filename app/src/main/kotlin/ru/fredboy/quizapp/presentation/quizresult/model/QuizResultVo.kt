package ru.fredboy.quizapp.presentation.quizresult.model

import nl.dionsegijn.konfetti.core.Party
import ru.fredboy.quizapp.domain.model.QuizStatus

data class QuizResultVo(
    val status: QuizStatus,
    val title: String,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val passingScore: Int,
    val parties: List<Party>,
)
